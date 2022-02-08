package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.validator.FileValidator
import com.jlefebure.spring.boot.minio.MinioException
import com.jlefebure.spring.boot.minio.MinioService
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import javax.servlet.http.HttpServletResponse
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class MinioController @Autowired constructor(
    private val minioService: MinioService,
    private val fileValidator: FileValidator
) {

    @GetMapping
    @Throws(MinioException::class)
    fun getListOfFiles(): ResponseEntity<List<FileInfoResponse>> {
        val files = FileFactory.createFileListResponse(minioService.list() ?: listOf())

        return ResponseEntity(files, HttpStatus.OK)
    }

    @GetMapping("/{fileName}")
    @Throws(MinioException::class, IOException::class)
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
    fun uploadFile(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("file_details") fileDetails: FileUploadRequest
    ): ResponseEntity<FileUploadResponse> {
        val validationResults = fileValidator.validateFileUploadRequest(file, fileDetails)

        if (validationResults.isNotEmpty()) {
            val response = FileFactory.createFileUploadResponse(file, validationResults)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }

        try {
            val pathOfFile = Path.of(file.originalFilename ?: file.name)
            minioService.upload(pathOfFile, file.inputStream, file.contentType)
            val response = FileFactory.createFileUploadResponse(file)

            return ResponseEntity(response, HttpStatus.OK)
        } catch (e: MinioException) {
            throw IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e)
        } catch (e: IOException) {
            throw IllegalStateException("The file ${file.name} cannot be read", e)
        }
    }
}
