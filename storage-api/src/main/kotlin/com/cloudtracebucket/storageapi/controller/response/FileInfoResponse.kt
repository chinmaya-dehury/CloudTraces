package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class FileInfoResponse {
    var provider: String? = null
    var fileFormat: String? = null
    var fileName: String? = null
    var fileSize: Long? = 0L
    var uploadTime: LocalDateTime? = null
}
