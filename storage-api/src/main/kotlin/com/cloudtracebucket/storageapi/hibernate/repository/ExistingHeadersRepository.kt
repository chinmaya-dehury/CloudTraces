package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExistingHeadersRepository : CrudRepository<ExistingHeaders, Long> {

    @Query(
        nativeQuery = true,
        value = """
        SELECT * FROM existing_headers e WHERE
        delete_time IS NOT NULL 
        AND (SELECT array_sort(:file_headers) = array_sort(e.headers))
        """
    )
    fun findByHeaders(@Param("file_headers") fileHeaders: List<String>): ExistingHeaders?
}
