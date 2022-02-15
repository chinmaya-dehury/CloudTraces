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
        array_sort(string_to_array(:file_headers, ',')) = array_sort(e.headers)
        AND delete_time IS NOT NULL 
        """
    )
    fun findByHeaders(@Param("file_headers") fileHeaders: String): ExistingHeaders?
}
