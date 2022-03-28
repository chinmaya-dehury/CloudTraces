package com.cloudtracebucket.storageapi.security

import com.cloudtracebucket.storageapi.controller.response.ErrorDetails
import com.cloudtracebucket.storageapi.exception.DataCollectorException
import com.cloudtracebucket.storageapi.exception.FileServiceException
import java.time.LocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(FileServiceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun fileAlreadyExistsException(e: FileServiceException): ResponseEntity<ErrorDetails> {
        val badRequest = HttpStatus.BAD_REQUEST
        val errorDetails = ErrorDetails(
            badRequest.value(),
            e.message ?: e.localizedMessage,
            LocalDateTime.now()
        )

        return ResponseEntity(errorDetails, badRequest)
    }

    @ExceptionHandler(DataCollectorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun dataCollectionException(e: DataCollectorException): ResponseEntity<ErrorDetails> {
        val badRequest = HttpStatus.BAD_REQUEST
        val errorDetails = ErrorDetails(
            badRequest.value(),
            e.message ?: e.localizedMessage,
            LocalDateTime.now()
        )

        return ResponseEntity(errorDetails, badRequest)
    }
}
