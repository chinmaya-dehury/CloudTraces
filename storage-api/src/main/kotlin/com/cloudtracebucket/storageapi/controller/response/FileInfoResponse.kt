package com.cloudtracebucket.storageapi.controller.response

import java.time.ZonedDateTime

class FileInfoResponse {
    var fileName: String? = null
    var fileSize: Long? = 0L
    var uploadTime: ZonedDateTime? = null
}
