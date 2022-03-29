package com.cloudtracebucket.storageapi.pojo.request

data class DataCollectorRequest(
    val uuid: String,
    val existingHeadersId: Long,
    val insertTime: String,
)
