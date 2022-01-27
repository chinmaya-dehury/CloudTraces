package com.cloudtracebucket.storageapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusController {

    @RequestMapping("/status", method = [RequestMethod.GET])
    @ResponseBody
    fun getStatus(): ResponseEntity<Map<String, Boolean>> {
        return ResponseEntity(
            mapOf("ok" to true),
            HttpStatus.OK
        )
    }
}
