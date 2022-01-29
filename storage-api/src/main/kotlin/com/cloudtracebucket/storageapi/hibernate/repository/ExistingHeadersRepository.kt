package com.cloudtracebucket.storageapi.hibernate.repository

import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import org.springframework.data.repository.CrudRepository

interface ExistingHeadersRepository : CrudRepository<ExistingHeaders, Long>
