package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.factory.ExistingHeadersFactory
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.utils.CsvUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService @Autowired constructor(
    private val fileMetaRepository: FileMetaRepository,
    private val headersRepository: ExistingHeadersRepository,
    private val existingHeadersFactory: ExistingHeadersFactory,
    private val fileFactory: FileFactory
) {
    fun processFile(file: MultipartFile, fileDetails: FileUploadRequest) {
        if (fileExists(file.originalFilename ?: file.name)) {
            throw FileServiceException("File ${file.originalFilename} already exists")
        }

        val fileHeaders = CsvUtil.getCsvHeaders(file, fileDetails.delimiter!!)

        if (fileHeaders.isEmpty()) {
            throw FileServiceException("File ${file.originalFilename} does not contain headers on the first row")
        }

        val stringifiedHeadersList = listToString(fileHeaders)
        var existingHeaders = getExistingHeaders(stringifiedHeadersList)

        if (existingHeaders == null) {
            existingHeaders = existingHeadersFactory.createHeadersEntity(file, fileDetails, stringifiedHeadersList)
            headersRepository.save(existingHeaders)
        }

        val fileMeta = fileFactory.createFileMetaEntity(file, fileDetails, existingHeaders)
        fileMetaRepository.save(fileMeta)
    }

    private fun fileExists(filename: String) = fileMetaRepository.findFirstByFileName(filename) != null

    private fun getExistingHeaders(headers: String): ExistingHeaders? {
        return headersRepository.findFirstByHeaders(headers)
    }

    private fun listToString(list: List<Any>) = list.joinToString(",")
}
