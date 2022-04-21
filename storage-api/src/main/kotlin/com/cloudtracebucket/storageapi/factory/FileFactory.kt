package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.FileMeta
import org.apache.commons.io.FilenameUtils.getExtension
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileFactory {
    fun createFileListResponse(files: List<FileMeta>) = files.map { file ->
        FileInfoResponse().also {
            it.provider = file.provider
            it.fileName = file.fileName
            it.fileSize = file.fileSize
            it.fileFormat = file.fileFormat
            it.uploadTime = file.uploadTime
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
