package com.cloudtracebucket.storageapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StorageApiApplication

fun main(args: Array<String>) {
    runApplication<StorageApiApplication>(*args)
}
