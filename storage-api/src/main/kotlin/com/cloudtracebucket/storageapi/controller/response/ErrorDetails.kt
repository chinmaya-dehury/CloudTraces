package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDetails(
    var status: Int,
    var message: String,
    var timestamp: LocalDateTime,
    var expectedHeaders: List<String>? = null,
    var actualHeaders: List<String>? = null,
)
