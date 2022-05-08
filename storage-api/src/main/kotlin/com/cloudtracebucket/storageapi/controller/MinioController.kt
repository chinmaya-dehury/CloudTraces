package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.service.FileService
import com.cloudtracebucket.storageapi.service.RestService
import com.cloudtracebucket.storageapi.utils.CsvUtil.getCsvHeaders
import com.cloudtracebucket.storageapi.validator.FileValidator
import com.jlefebure.spring.boot.minio.MinioException
import com.jlefebure.spring.boot.minio.MinioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import javax.servlet.http.HttpServletResponse
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class MinioController @Autowired constructor(
    private val minioService: MinioService,
    private val fileValidator: FileValidator,
    private val fileFactory: FileFactory,
    private val fileService: FileService,
    private val restService: RestService,
    private val fileMetaRepository: FileMetaRepository
) {

    @GetMapping
    @Throws(MinioException::class)
    @Operation(summary = "Returns a list of files available for downloading from file storage", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "400", description = "Bad request"),
        ]
    )
    fun getListOfFiles(): ResponseEntity<List<FileInfoResponse>> {
        val files = fileMetaRepository.findAllFiles() ?: listOf()
        val response = fileFactory.createFileListResponse(files)

        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{fileName}")
    @Throws(MinioException::class, IOException::class)
    @Operation(summary = "Download a file by its filename from file storage", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "404", description = "Not found"),
        ]
    )
    fun getFile(
        @PathVariable("fileName") fileName: String,
        response: HttpServletResponse
    ) {
        val inputStream: InputStream = minioService.get(Path.of(fileName))

        response.addHeader("Content-disposition", "attachment;filename=$fileName")
        response.contentType = "multipart/form-data"

        IOUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
    }

    @PostMapping
    @Throws(IllegalStateException::class)
    @Operation(summary = "Uploads a cloud trace data file to file storage and processes the file", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "400", description = "Bad request"),
        ]
    )
    fun uploadFile(
        @RequestPart("file") file: MultipartFile,
        fileDetails: FileUploadRequest
    ): ResponseEntity<FileUploadResponse> {
        val validationResults = fileValidator.validateFileUploadRequest(file, fileDetails)

        if (validationResults.isNotEmpty()) {
            val response = fileFactory.createFileUploadResponse(file, false, validationResults)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }

        try {
            val headers = getCsvHeaders(file, fileDetails.delimiter!!)
            // needed for creating dynamic table creation
            fileDetails.originalFilename = file.originalFilename ?: file.name

            val formattedFile = fileService.prepareFileForProcessing(file, fileDetails, headers)
            val pathOfFile = Path.of(formattedFile.originalFilename ?: formattedFile.name)

            minioService.upload(pathOfFile, formattedFile.inputStream, formattedFile.contentType)

            val dataCollectorRequest = fileService.processFile(formattedFile, fileDetails, headers)

            val collectedData = restService.postTriggerDataCollector(dataCollectorRequest)
            val dataCollected = collectedData?.insertedRows!! > 0

            val responseStatus = if (collectedData.errors.isNotEmpty()) {
                HttpStatus.BAD_REQUEST
            } else {
                HttpStatus.OK
            }

            val response = fileFactory.createFileUploadResponse(file, dataCollected, collectedData.errors)
            return ResponseEntity(response, responseStatus)
        } catch (e: MinioException) {
            throw IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e)
        } catch (e: IOException) {
            throw IllegalStateException("The file ${file.name} cannot be read", e)
        }
    }
}
