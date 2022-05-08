package com.cloudtracebucket.storageapi.controller.request

import com.cloudtracebucket.storageapi.pojo.enums.CsvStandardDelimiter
import com.cloudtracebucket.storageapi.pojo.enums.TraceType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import net.sf.oval.constraint.NotEmpty
import net.sf.oval.constraint.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Model for a cloud trace file uploading")
class FileUploadRequest {
    @NotNull(message = "Provider cannot be null or empty")
    @NotEmpty
    @field:Schema(
        description = "A provider of the cloud trace file",
        example = "Alibaba",
        type = "string",
        minLength = 1,
        required = true
    )
    var provider: String? = null

    @NotNull(message = "Trace type cannot be null or empty")
    @NotEmpty
    @field:Schema(
        description = "A trace type of the cloud trace file",
        example = "SERVERLESS_PLATFORM",
        type = "enum",
        required = true
    )
    var traceType: TraceType? = null

    @NotNull(message = "File delimiter cannot be null or empty")
    @NotEmpty
    @field:Schema(
        description = "A delimiter of the cloud trace file",
        example = "COMMA_SEPARATED",
        type = "enum",
        required = true
    )
    var delimiter: CsvStandardDelimiter? = null

    var originalFilename: String? = null
}
