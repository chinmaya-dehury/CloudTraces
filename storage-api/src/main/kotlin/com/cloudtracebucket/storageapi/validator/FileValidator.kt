package com.cloudtracebucket.storageapi.validator

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.utils.FileUtil.CSV_MIME_TYPE
import net.sf.oval.Validator
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class FileValidator {

    private val ovalValidator: Validator = Validator()
    private val FILE_LIMIT_SIZE = 157286400

    fun validateFileUploadRequest(
        file: MultipartFile,
        fileUploadRequest: FileUploadRequest
    ): List<String> {
        val errors: MutableList<String> = mutableListOf()
        val violations = ovalValidator.validate(fileUploadRequest)

        when {
            violations.isNotEmpty() -> {
                violations.forEach { violation ->
                    errors.add(violation.message)
                }
            }
            file.isEmpty -> errors.add("File can not be empty")
            !CSV_MIME_TYPE.equals(file.contentType, ignoreCase = true) -> errors.add("Uploaded file is not a valid .csv file")
            file.size > FILE_LIMIT_SIZE -> errors.add("File size exceeded 150MB")
        }

        return errors
    }
}
