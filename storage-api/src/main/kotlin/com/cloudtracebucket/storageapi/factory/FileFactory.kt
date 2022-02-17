package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import io.minio.messages.Item
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileFactory {
    fun createFileListResponse(files: List<Item>) = files.map { file ->
        FileInfoResponse().also {
            it.fileName = file.objectName()
            it.fileSize = file.size()
            it.uploadTime = file.lastModified()
        }
    }

    fun createFileUploadResponse(file: MultipartFile, errors: List<String> = listOf()) = FileUploadResponse().also {
        it.fileName = file.originalFilename ?: file.name
        it.errors = errors
    }

//    fun createFileMeta(file: MultipartFile, fileDetails: FileUploadRequest): FileMeta {
//
//    }
}
