package com.cloudtracebucket.storageapi.controller

import com.jlefebure.spring.boot.minio.MinioException
import com.jlefebure.spring.boot.minio.MinioService
import io.minio.messages.Item
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
import java.nio.file.Path
import javax.servlet.http.HttpServletResponse
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class MinioController @Autowired constructor(
    private val minioService: MinioService
) {

    @GetMapping
    @Throws(MinioException::class)
    fun getListOfFiles(): ResponseEntity<List<Item>> {
        val files = minioService.list() ?: listOf()

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
        response.contentType = URLConnection.guessContentTypeFromName(fileName)

        IOUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
    }

    @PostMapping
    @Throws(IllegalStateException::class)
    fun uploadFile(
        @RequestParam("file") file: MultipartFile
    ) {
        val pathOfFile = Path.of(file.originalFilename ?: file.name)

        try {
            minioService.upload(pathOfFile, file.inputStream, file.contentType)
        } catch (e: MinioException) {
            throw IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e)
        } catch (e: IOException) {
            throw IllegalStateException("The file ${file.name} cannot be read", e)
        }
    }
}
