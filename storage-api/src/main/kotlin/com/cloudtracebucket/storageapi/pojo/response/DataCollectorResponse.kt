package com.cloudtracebucket.storageapi.pojo.response

data class DataCollectorResponse(
    val insertedRows: Int,
    val errors: List<String>,
    val timestamp: String
)
