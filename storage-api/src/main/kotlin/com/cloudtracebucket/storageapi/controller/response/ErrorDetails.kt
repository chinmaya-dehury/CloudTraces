package com.cloudtracebucket.storageapi.controller.response

import java.time.LocalDateTime

data class ErrorDetails(
    var timeStamp: LocalDateTime,
    var status: Int,
    var message: String,
)
