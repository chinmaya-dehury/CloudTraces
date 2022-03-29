package com.cloudtracebucket.storageapi.service

import Constants.MINIO_BUCKET_NAME
import Constants.MINIO_INTERNAL_HOST
import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.exception.FileServiceException
import com.cloudtracebucket.storageapi.exception.UtilException
import com.cloudtracebucket.storageapi.factory.DataCollectorFactory
import com.cloudtracebucket.storageapi.factory.ExistingHeadersFactory
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.hibernate.ExistingHeaders
import com.cloudtracebucket.storageapi.hibernate.repository.DynamicTableRepository
import com.cloudtracebucket.storageapi.hibernate.repository.ExistingHeadersRepository
import com.cloudtracebucket.storageapi.hibernate.repository.FileMetaRepository
import com.cloudtracebucket.storageapi.pojo.request.DataCollectorRequest
import com.cloudtracebucket.storageapi.utils.CsvUtil.getCsvHeaders
import com.cloudtracebucket.storageapi.utils.CsvUtil.getDelimiterSetting
import com.cloudtracebucket.storageapi.utils.CsvUtil.listToString
import com.cloudtracebucket.storageapi.utils.CsvUtil.replaceHeadersInFile
import java.time.LocalDateTime
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService @Autowired constructor(
    private val fileMetaRepository: FileMetaRepository,
    private val headersRepository: ExistingHeadersRepository,
    private val existingHeadersFactory: ExistingHeadersFactory,
    private val dynamicTableRepository: DynamicTableRepository,
    private val fileFactory: FileFactory,
    private val dataCollectorFactory: DataCollectorFactory,
) {
    fun processFile(
        formattedFile: MultipartFile,
        fileDetails: FileUploadRequest,
        fileHeaders: List<String>,
    ): DataCollectorRequest {
        val latestInsertTime: String?
        var dynamicTableName = generateTargetTableName(fileDetails)
        val (_, delimiter) = getDelimiterSetting(fileDetails.delimiter!!)
        val fileUrl = getMinioObjectUrl(formattedFile.originalFilename ?: formattedFile.name)
        val stringifiedHeadersList = listToString(fileHeaders)
        var existingHeaders = checkIfHeadersExist(dynamicTableName)

        when (existingHeaders) {
            null -> {
                existingHeaders = existingHeadersFactory.createHeadersEntity(
                    fileDetails,
                    stringifiedHeadersList,
                    dynamicTableName
                )

                with(dynamicTableRepository) {
                    createTableFromCsv(dynamicTableName, fileUrl, delimiter, fileHeaders.size)
                    latestInsertTime = getLatestInsertTime(dynamicTableName)
                }

                headersRepository.save(existingHeaders)
            }
            else -> {
                val incomingHeadersValid = checkIfIncomingHeadersAreValid(
                    stringifiedHeadersList,
                    existingHeaders.headersListAsString!!
                )

                if (!incomingHeadersValid) {
                    throw FileServiceException(
                        "Table for provider ${fileDetails.provider} with trace type ${fileDetails.traceType} already exists, but headers in file differs from headers in the system",
                        existingHeaders.headersListAsString!!,
                        stringifiedHeadersList
                    )
                }

                dynamicTableName = existingHeaders.dynamicTableName!!

                with(dynamicTableRepository) {
                    insertCsvToExistingTable(dynamicTableName, fileUrl, delimiter)
                    latestInsertTime = getLatestInsertTime(dynamicTableName)
                }

                existingHeaders.updateTime = LocalDateTime.now()
                headersRepository.save(existingHeaders)
            }
        }

        val fileMeta = fileFactory.createFileMetaEntity(formattedFile, fileDetails, existingHeaders)
        fileMetaRepository.save(fileMeta)

        // after processing csv, prepare data collector request
        return dataCollectorFactory.createRequest(
            UUID.randomUUID().toString(),
            existingHeaders.id!!,
            latestInsertTime!!
        )
    }

    fun prepareFileForProcessing(
        multipartFile: MultipartFile,
        fileDetails: FileUploadRequest,
        fileHeaders: List<String>,
    ): MultipartFile {
        if (fileHeaders.isEmpty()) {
            throw FileServiceException("File ${multipartFile.originalFilename} does not contain headers on the first row")
        }

        val headersListAsString = listToString(fileHeaders, fileDetails.delimiter)

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
        return headersRepository.findFirstByDynamicTableName(headers)
    }

    private fun getMinioObjectUrl(filename: String): String {
        return "$MINIO_INTERNAL_HOST/$MINIO_BUCKET_NAME/$filename"
    }

    private fun generateTargetTableName(fileDetails: FileUploadRequest): String {
        val formattedProvider = fileDetails.provider!!
            .trim()
            .replace("\\s+".toRegex(), "_")

        return "${formattedProvider}_${fileDetails.traceType}".lowercase()
    }

    private fun checkIfIncomingHeadersAreValid(expectedHeaders: String, actualHeaders: String): Boolean {
        return actualHeaders.equals(expectedHeaders, ignoreCase = true)
    }
}
