package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.FileMeta
import org.springframework.data.repository.CrudRepository

interface FileMetaRepository : CrudRepository<FileMeta, Long>
