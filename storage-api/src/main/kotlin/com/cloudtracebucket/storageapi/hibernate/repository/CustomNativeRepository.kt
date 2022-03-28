package com.cloudtracebucket.storageapi.hibernate.repository

interface CustomNativeRepository {
    fun createTableFromCsv(
        targetTableName: String,
        csvPath: String,
        delimiter: String,
        columnCount: Int
    )

    fun insertCsvToExistingTable(
        targetTableName: String,
        csvPath: String,
        delimiter: String
    )

    fun getLatestInsertTime(dynamicTblName: String): String?
}
