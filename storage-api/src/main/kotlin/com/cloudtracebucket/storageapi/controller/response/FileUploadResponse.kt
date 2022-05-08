package com.cloudtracebucket.storageapi.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Model for file upload response")
class FileUploadResponse {
    @field:Schema(
        description = "File name",
        example = "test.csv",
        type = "string"
    )
    var fileName: String? = null
    @field:Schema(
        description = "List of errors returned from data colletor",
        example = "[\"No records have been inserted lately\"]",
        type = "list"
    )
    var errors: List<String>? = listOf()
    @field:Schema(
        description = "A flag informing if the data was collected from uploaded file",
        example = "true",
        type = "boolean"
    )
    var dataCollected: Boolean = false
    @field:Schema(
        description = "An informative message of file uploading result",
        example = "File test.csv uploaded successfully",
        type = "string"
    )
    var message: String? = null
}
