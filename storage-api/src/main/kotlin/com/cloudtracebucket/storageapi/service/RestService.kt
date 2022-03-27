package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.exception.DataCollectorException
import com.cloudtracebucket.storageapi.pojo.request.DataCollectorRequest
import com.cloudtracebucket.storageapi.pojo.response.DataCollectorResponse
import java.time.Duration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.jvm.Throws

@Service
class RestService {

    private val restTemplate: RestTemplate = RestTemplateBuilder()
        .setConnectTimeout(Duration.ofSeconds(30L))
        .build()

    @Throws(DataCollectorException::class)
    fun postTriggerDataCollector(reqBody: DataCollectorRequest): DataCollectorResponse? {
        val url = getDataCollectorUrl()
        val httpHeaders = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            accept = listOf(MediaType.APPLICATION_JSON)
            set("x-request-source", "storage-api")
        }

        val body = mapOf<String, Any>(
            "uuid" to reqBody.uuid,
            "existingHeadersId" to reqBody.existingHeadersId,
            "insertTime" to reqBody.insertTime
        )

        val entity: HttpEntity<Map<String, Any>> = HttpEntity(body, httpHeaders)

        val response: ResponseEntity<DataCollectorResponse> = restTemplate.postForEntity(
            url,
            entity,
            DataCollectorResponse::class.java
        )

        return if (response.statusCode == HttpStatus.OK) {
            response.body
        } else {
            val errors = response.body?.errors?.joinToString("-") ?: "Data Collector internal error"
            throw DataCollectorException(errors)
        }
    }

    private fun getDataCollectorUrl(): String {
        return "${Constants.DATA_COLLECTOR_URL}/collect-data"
    }
}