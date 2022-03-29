package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExistingHeadersRepository : CrudRepository<ExistingHeaders, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM existing_headers 
        WHERE dynamic_table_name = :dynamic_tbl_name
        AND delete_time IS NULL
    """)
    fun findFirstByDynamicTableName(@Param("dynamic_tbl_name") dynamicTableName: String): ExistingHeaders?
}
