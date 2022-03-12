package com.cloudtracebucket.storageapi.hibernate.repository

import javax.persistence.EntityManager
import javax.persistence.ParameterMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CsvToTableRepository @Autowired constructor(
    private val entityManager: EntityManager,
) : CustomNativeRepository {

    override fun createTableFromCsv(
        targetTableName: String,
        csvPath: String,
        delimiter: String,
        columnCount: Int,
    ) {
        entityManager.createStoredProcedureQuery("load_csv_file")
            .registerStoredProcedureParameter("target_table", String::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("csv_path", String::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("delimiter", String::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("col_count", Integer::class.java, ParameterMode.IN)
            .setParameter("target_table", targetTableName)
            .setParameter("csv_path", csvPath)
            .setParameter("delimiter", delimiter)
            .setParameter("col_count", columnCount)
            .singleResult
    }

    override fun insertCsvToExistingTable(
        targetTableName: String,
        csvPath: String,
        delimiter: String
    ) {
        entityManager.createStoredProcedureQuery("insert_csv_to_existing_table")
            .registerStoredProcedureParameter("target_table", String::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("csv_path", String::class.java, ParameterMode.IN)
            .registerStoredProcedureParameter("delimiter", String::class.java, ParameterMode.IN)
            .setParameter("target_table", targetTableName)
            .setParameter("csv_path", csvPath)
            .setParameter("delimiter", delimiter)
            .singleResult
    }
}
