package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.utils.CsvReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {

    @Autowired
    private val fileMetaRepository: FileMetaRepository? = null

    @Autowired
    private val headersRepository: ExistingHeadersRepository? = null

    fun processFile(file: MultipartFile, fileDetails: FileUploadRequest) {
        if (fileExists(file.originalFilename ?: file.name)) {
            throw FileServiceException("File ${file.originalFilename} already exists")
        }

        val fileHeaders = CsvReader.getCsvHeadersAsList(file, fileDetails.delimiter!!)
        val existingHeaders = getExistingHeaders(fileHeaders)
    }

    private fun fileExists(filename: String) = fileMetaRepository?.existsByFileName(filename) ?: false

    private fun getExistingHeaders(headers: List<String>): ExistingHeaders? {
        return headersRepository?.findByHeaders(headers)
    }
}
