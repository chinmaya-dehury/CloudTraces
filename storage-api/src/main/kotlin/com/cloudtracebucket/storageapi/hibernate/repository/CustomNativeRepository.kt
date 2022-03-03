package com.cloudtracebucket.storageapi.hibernate.repository

interface CustomNativeRepository {
    fun createSchemaFromCsv(
        targetTableName: String,
        csvPath: String,
        delimiter: String,
        columnCount: Int
    )

    fun insertCsvToExistingSchema(
        targetTableName: String,
        csvPath: String,
        delimiter: String
    )
}
