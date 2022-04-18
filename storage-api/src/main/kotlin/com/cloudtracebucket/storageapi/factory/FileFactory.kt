package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.FileMeta
import io.minio.messages.Item
import org.apache.commons.io.FilenameUtils.getExtension
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

    fun createFileUploadResponse(
        file: MultipartFile,
        dataCollected: Boolean = false,
        errors: List<String> = listOf(),
    ) = FileUploadResponse().also {
        it.fileName = file.originalFilename ?: file.name
        it.errors = errors
        it.dataCollected = dataCollected
        it.message = "File ${file.originalFilename} uploaded successfully"
        it.status = 200
    }

    fun createFileMetaEntity(
        file: MultipartFile,
        fileDetails: FileUploadRequest,
        existingHeader: ExistingHeaders,
    ) = FileMeta().also {
        it.fileName = file.originalFilename ?: file.name
        it.fileSize = file.size
        it.fileFormat = getExtension(file.originalFilename)
        it.provider = fileDetails.provider
        it.existingHeader = existingHeader
    }
}
