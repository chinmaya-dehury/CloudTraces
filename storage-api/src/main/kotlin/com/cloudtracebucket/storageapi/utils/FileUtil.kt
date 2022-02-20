package com.cloudtracebucket.storageapi.utils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import org.apache.commons.io.IOUtils
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

object FileUtil {
    val CSV_MIME_TYPE = "text/csv"

    @Throws(IOException::class)
    fun multipartFileToFile(multipartFile: MultipartFile): File {
        val convFile = File(multipartFile.originalFilename ?: multipartFile.name)

        convFile.createNewFile()

        multipartFile.inputStream.use {
                inputStream -> Files.copy(inputStream, convFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }

        return convFile
    }

    @Throws(IOException::class)
    fun fileToMultipartFile(file: File, contentType: String): MultipartFile {
        val input = FileInputStream(file)

        return MockMultipartFile(file.name, file.name, contentType, IOUtils.toByteArray(input))
    }
}