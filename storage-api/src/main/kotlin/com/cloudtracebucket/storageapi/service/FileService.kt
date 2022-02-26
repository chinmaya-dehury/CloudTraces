package com.cloudtracebucket.storageapi.service

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.exception.UtilException
import com.cloudtracebucket.storageapi.factory.ExistingHeadersFactory
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.repository.CsvToTableRepository
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.utils.CsvUtil.getCsvHeaders
import com.cloudtracebucket.storageapi.utils.CsvUtil.getDelimiterSetting
import com.cloudtracebucket.storageapi.utils.CsvUtil.listToString
import com.cloudtracebucket.storageapi.utils.CsvUtil.replaceHeadersInFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService @Autowired constructor(
    private val fileMetaRepository: FileMetaRepository,
    private val headersRepository: ExistingHeadersRepository,
    private val existingHeadersFactory: ExistingHeadersFactory,
    private val csvToTableRepository: CsvToTableRepository,
    private val fileFactory: FileFactory,
) {
    fun processFile(
        formattedFile: MultipartFile,
        fileDetails: FileUploadRequest,
        fileHeaders: List<String>
    ) {
        val delimiter = getDelimiterSetting(fileDetails.delimiter!!)

        val stringifiedHeadersList = listToString(fileHeaders)
        var existingHeaders = checkIfHeadersExist(stringifiedHeadersList)

        if (existingHeaders == null) {
            existingHeaders = existingHeadersFactory.createHeadersEntity(fileDetails, stringifiedHeadersList)
            val fileUrl = getMinioObjectUrl(formattedFile.originalFilename ?: formattedFile.name)
            csvToTableRepository.runNativeQuery(    "test_schema", fileUrl, delimiter.second, fileHeaders.size)
            headersRepository.save(existingHeaders)
        }

        val fileMeta = fileFactory.createFileMetaEntity(formattedFile, fileDetails, existingHeaders)
        fileMetaRepository.save(fileMeta)
    }

    fun prepareFileForProcessing(
        multipartFile: MultipartFile,
        fileDetails: FileUploadRequest,
    ): MultipartFile {
        val fileHeaders = getCsvHeaders(multipartFile, fileDetails.delimiter!!)
        val headersListAsString = listToString(fileHeaders, fileDetails.delimiter)

        if (fileHeaders.isEmpty()) {
            throw FileServiceException("File ${multipartFile.originalFilename} does not contain headers on the first row")
        }

        return try {
            replaceHeadersInFile(multipartFile, headersListAsString)
        } catch (e: UtilException) {
            throw FileServiceException(
                "File ${multipartFile.originalFilename} failed to prepare for processing: ${e.message}",
                e
            )
        }
    }

    private fun checkIfHeadersExist(headers: String): ExistingHeaders? {
        return headersRepository.findFirstByHeaders(headers)
    }

    private fun getMinioObjectUrl(filename: String): String {
        return "http://minio:9000/cloud-trace-data-files/$filename"
    }
}
