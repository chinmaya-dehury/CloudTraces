package com.cloudtracebucket.storageapi.controller.response

import java.time.LocalDateTime

data class ErrorDetails(
    var status: Int,
    var message: String,
    var timestamp: LocalDateTime,
)
