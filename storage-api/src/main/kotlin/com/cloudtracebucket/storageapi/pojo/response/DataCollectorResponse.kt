package com.cloudtracebucket.storageapi.pojo.response

data class DataCollectorResponse(
    val insertedRows: Int,
    val errors: List<Any>,
    val timestamp: String
)
