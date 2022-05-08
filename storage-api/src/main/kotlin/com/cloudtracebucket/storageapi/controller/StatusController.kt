package com.cloudtracebucket.storageapi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusController {

    @RequestMapping("/status", method = [RequestMethod.GET])
    @Operation(summary = "Health check of Storage API", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "400", description = "Bad request"),
        ]
    )
    fun getStatus(): ResponseEntity<Map<String, Boolean>> {
        return ResponseEntity(
            mapOf("ok" to true),
            HttpStatus.OK
        )
    }
}
