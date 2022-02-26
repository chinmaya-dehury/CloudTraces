package com.cloudtracebucket.storageapi.exception

open class FileServiceException : RuntimeException {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}
