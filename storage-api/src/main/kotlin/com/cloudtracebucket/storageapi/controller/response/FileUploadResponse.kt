package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class FileUploadResponse {
    var fileName: String? = null
    var errors: List<String>? = listOf()
    var dataCollected: Boolean = false
    var status: Int? = null
    var message: String? = null
}
