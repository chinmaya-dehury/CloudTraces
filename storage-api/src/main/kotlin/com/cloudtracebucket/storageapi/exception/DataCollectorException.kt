package com.cloudtracebucket.storageapi.exception

open class DataCollectorException : RuntimeException {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}
