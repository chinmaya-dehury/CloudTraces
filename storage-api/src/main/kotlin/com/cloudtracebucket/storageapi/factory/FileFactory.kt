package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import io.minio.messages.Item

object FileFactory {
    fun createFileListResponse(files: List<Item>) = files.map { file ->
        FileInfoResponse().also {
            it.fileName = file.objectName()
            it.fileSize = file.size()
            it.uploadTime = file.lastModified()
        }
    }
}
