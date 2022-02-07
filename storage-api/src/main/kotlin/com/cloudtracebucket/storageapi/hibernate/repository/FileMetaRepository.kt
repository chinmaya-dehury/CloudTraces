package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.FileMeta
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FileMetaRepository : CrudRepository<FileMeta, Long> {
    fun existsByFileName(filename: String): Boolean
}
