package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.stereotype.Component

@Component
class ExistingHeadersFactory {
    fun createHeadersEntity(
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
