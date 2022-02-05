package com.cloudtracebucket.storageapi.validator

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class FileValidator : Validator {

    private val CSV_MIME_TYPE = "text/csv"
    private val FILE_LIMIT_SIZE = 157286400

    override fun supports(clazz: Class<*>): Boolean {
        return FileUploadRequest::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        val fileUpload = target as FileUploadRequest
        val file = fileUpload.file

        when {
            (file.isEmpty) -> errors.rejectValue("file", "upload.file.required")
            (!CSV_MIME_TYPE.equals(file.contentType, ignoreCase = true)) -> errors.rejectValue(
                "file",
                "upload.invalid.file.type"
            )
            (file.size > FILE_LIMIT_SIZE) -> errors.rejectValue("file", "upload.exceeded.file.size")
        }
    }
}
