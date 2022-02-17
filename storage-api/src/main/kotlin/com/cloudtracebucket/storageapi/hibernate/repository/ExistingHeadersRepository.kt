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
        WHERE file_headers = :headers_list_as_string
        AND delete_time IS NULL
    """)
    fun findFirstByHeaders(@Param("headers_list_as_string") fileHeaders: String): ExistingHeaders?
}
