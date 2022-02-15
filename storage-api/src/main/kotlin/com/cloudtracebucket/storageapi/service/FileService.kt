package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.factory.ExistingHeadersFactory
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.utils.CsvReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService @Autowired constructor(
    private val fileMetaRepository: FileMetaRepository,
    private val headersRepository: ExistingHeadersRepository,
    private val existingHeadersFactory: ExistingHeadersFactory
) {
    fun processFile(file: MultipartFile, fileDetails: FileUploadRequest) {
        if (fileExists(file.originalFilename ?: file.name)) {
            throw FileServiceException("File ${file.originalFilename} already exists")
        }

        val fileHeaders = CsvReader.getCsvHeadersAsList(file, fileDetails.delimiter!!)
        val existingHeaders = getExistingHeaders(fileHeaders)

        if (existingHeaders == null) {
            val newExistingHeaders = existingHeadersFactory.createHeadersData(file, fileDetails, fileHeaders)
            headersRepository.save(newExistingHeaders)
        }
    }

    private fun fileExists(filename: String) = fileMetaRepository.findFirstByFileName(filename) != null

    private fun getExistingHeaders(headers: String): ExistingHeaders? {
        return headersRepository.findByHeaders(headers)
    }
}
