package com.cloudtracebucket.storageapi.hibernate.repository

interface CustomNativeRepository {
    fun runNativeQuery(
        targetTableName: String,
        csvPath: String,
        delimiter: String,
        columnCount: Int
    )
}
