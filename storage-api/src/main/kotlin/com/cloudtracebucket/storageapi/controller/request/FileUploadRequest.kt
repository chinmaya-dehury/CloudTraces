package com.cloudtracebucket.storageapi.controller.request

import com.cloudtracebucket.storageapi.pojo.enums.TraceType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import net.sf.oval.constraint.NotEmpty
import net.sf.oval.constraint.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class FileUploadRequest {
    @NotNull(message = "Provider cannot be null")
    @NotEmpty
    var provider: String? = null

    @NotNull(message = "Trace type cannot be null")
    @NotEmpty
    var traceType: TraceType? = null
}
