package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {

    @Autowired
    private val fileMetaRepository: FileMetaRepository? = null

    @Autowired
    private val headersRepository: ExistingHeadersRepository? = null

    fun processFile(file: MultipartFile) {
        if (fileExists(file.originalFilename ?: file.name)) {
            throw FileServiceException("File ${file.originalFilename} already exists")
        }
    }

    private fun fileExists(filename: String) = fileMetaRepository?.existsByFileName(filename) ?: false
}
