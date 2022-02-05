package com.cloudtracebucket.storageapi.validator

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileValidator {

    private val CSV_MIME_TYPE = "text/csv"
    private val FILE_LIMIT_SIZE = 157286400

    fun validate(file: MultipartFile): List<String> {
        val errors: MutableList<String> = mutableListOf()

        when {
            (file.isEmpty) -> errors.add("File can not be empty")
            (!CSV_MIME_TYPE.equals(file.contentType, ignoreCase = true))
                -> errors.add("Uploaded file is not a valid .csv file")
            (file.size > FILE_LIMIT_SIZE) -> errors.add("File size exceeded 150MB")
        }
        return errors
    }
}
