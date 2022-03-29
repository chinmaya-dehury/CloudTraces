package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class FileInfoResponse {
    var fileName: String? = null
    var fileSize: Long? = 0L
    var uploadTime: ZonedDateTime? = null
}
