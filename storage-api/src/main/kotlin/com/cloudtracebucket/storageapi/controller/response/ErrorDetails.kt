package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Model for error details if response is unsuccessful")
data class ErrorDetails(
    @field:Schema(
        description = "Status of HTTP response",
        example = "400",
        type = "int"
    )
    var status: Int,
    @field:Schema(
        description = "Error message",
        example = "The file cannot be upload on the internal storage. Please retry later",
        type = "string"
    )
    var message: String,
    @field:Schema(
        description = "Timestamp of the error",
        type = "datetime"
    )
    var timestamp: LocalDateTime
)
