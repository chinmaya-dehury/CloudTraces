package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ExistingHeadersFactory {
    fun createHeadersEntity(
        file: MultipartFile,
        fileDetails: FileUploadRequest,
        fileHeaders: String
    ): ExistingHeaders {
        return ExistingHeaders().also {
            it.provider = fileDetails.provider
            it.traceType = fileDetails.traceType
            it.headersListAsString = fileHeaders
        }
    }
}