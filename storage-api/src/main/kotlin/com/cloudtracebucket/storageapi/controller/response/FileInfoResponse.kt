package com.cloudtracebucket.storageapi.controller.response

import com.cloudtracebucket.storageapi.pojo.enums.TraceType
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Model for file meta data response")
class FileInfoResponse {
    @field:Schema(
        description = "File name",
        example = "test.csv",
        type = "string"
    )
    var fileName: String? = null
    @field:Schema(
        description = "File format",
        example = "csv",
        type = "string"
    )
    var fileFormat: String? = null
    @field:Schema(
        description = "File's provider",
        example = "Alibaba",
        type = "string"
    )
    var provider: String? = null
    @field:Schema(
        description = "File size in bytes",
        example = "42069",
        type = "long"
    )
    var fileSize: Long? = 0L
    @field:Schema(
        description = "A trace type of the cloud trace file",
        example = "SERVERLESS_PLATFORM",
        type = "enum",
    )
    var traceType: TraceType? = null
    @field:Schema(
        description = "File's upload time",
        type = "datetime",
    )
    var uploadTime: LocalDateTime? = null
}
