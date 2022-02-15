package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.FileMeta
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FileMetaRepository : CrudRepository<FileMeta, Long> {
    @Query(nativeQuery = true, value = """
        SELECT * FROM file_meta 
        WHERE file_name = :file_name
        AND delete_time IS NULL
        LIMIT 1;
    """)
    fun findFirstByFileName(@Param("file_name") filename: String): FileMeta?
}
