package com.cloudtracebucket.storageapi.factory

import Constants.targetSchemaMap
import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.stereotype.Component

@Component
class ExistingHeadersFactory {
    fun createHeadersEntity(
        fileDetails: FileUploadRequest,
        fileHeaders: String,
        dynamicTableName: String
    ): ExistingHeaders {
        return ExistingHeaders().also {
            it.provider = fileDetails.provider?.lowercase()
            it.traceType = fileDetails.traceType
            it.headersListAsString = fileHeaders
            it.dynamicSchemaName = dynamicTableName
            it.targetSchema = targetSchemaMap[fileDetails.traceType]
        }
    }
}
