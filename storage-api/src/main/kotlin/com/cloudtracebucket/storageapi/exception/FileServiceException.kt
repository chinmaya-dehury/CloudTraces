package com.cloudtracebucket.storageapi.exception

open class FileServiceException : RuntimeException {
    var expectedHeaders: String? = null
    var actualHeaders: String? = null

    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
    constructor(message: String, expectedHeaders: String, actualHeaders: String) : super(message) {
        this.expectedHeaders = expectedHeaders
        this.actualHeaders = actualHeaders
    }
}
