package com.cloudtracebucket.storageapi.exception

open class FileServiceException: RuntimeException {
    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message)
}
