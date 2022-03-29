package com.cloudtracebucket.storageapi.errorHandlers

import com.cloudtracebucket.storageapi.exception.DataCollectorException
import com.cloudtracebucket.storageapi.pojo.response.DataCollectorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.ResponseErrorHandler
import kotlin.jvm.Throws

@Component
class RestServiceErrorHandler: ResponseErrorHandler {
    private val objectMapper = ObjectMapper()

    override fun hasError(response: ClientHttpResponse): Boolean {
        return DefaultResponseErrorHandler().hasError(response)
    }

    @Throws(DataCollectorException::class)
    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode.is4xxClientError) {
            val mappedResponse = objectMapper.readValue(response.body, DataCollectorResponse::class.java)
            val errors = mappedResponse.errors.joinToString(",")
            throw DataCollectorException(errors)
        }

        if (response.statusCode.is5xxServerError) {
            throw DataCollectorException("Data Collector internal error")
        }
    }
}
