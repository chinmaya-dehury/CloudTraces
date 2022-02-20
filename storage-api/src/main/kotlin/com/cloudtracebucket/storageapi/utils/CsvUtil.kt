package com.cloudtracebucket.storageapi.utils

import com.cloudtracebucket.storageapi.pojo.enums.CsvStandardDelimiter
import com.cloudtracebucket.storageapi.utils.FileUtil.CSV_MIME_TYPE
import java.io.FileWriter
import java.io.InputStreamReader
import org.springframework.web.multipart.MultipartFile
import org.supercsv.io.CsvBeanReader
import org.supercsv.io.CsvBeanWriter
import org.supercsv.prefs.CsvPreference


object CsvUtil {

    fun getCsvHeaders(file: MultipartFile, delimiter: CsvStandardDelimiter): List<String> {
        val delimiterSetting = getDelimiterSetting(delimiter)
        val beanReader = CsvBeanReader(InputStreamReader(file.inputStream), delimiterSetting)
        val headers = beanReader.getHeader(true)
            .map { formatHeader(it) }
            .sorted()
        println(headers)
        return headers
    }

    private fun getDelimiterSetting(delimiter: CsvStandardDelimiter): CsvPreference {
        return when (delimiter) {
            CsvStandardDelimiter.COMMA_SEPARATED -> CsvPreference.STANDARD_PREFERENCE
            CsvStandardDelimiter.SEMICOLON_SEPARATED -> CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE
            CsvStandardDelimiter.TAB_SEPARATED -> CsvPreference.TAB_PREFERENCE
            CsvStandardDelimiter.PIPE_SEPARATED -> CsvPreference
                .Builder('"', '|'.code, "\n")
                .build()
            else -> CsvPreference.STANDARD_PREFERENCE
        }
    }

    private fun formatHeader(header: String): String {
        return header
            .lowercase()
            .trim()
            .replace("\\[|\\]".toRegex(), "")
            .replace("\\s".toRegex(), "_")
    }

    fun overrideCsvHeaders(
        formattedHeaders: List<String>,
        multipartFile: MultipartFile,
        delimiter: CsvStandardDelimiter,
    ): MultipartFile {
        val delimiterSetting = getDelimiterSetting(delimiter)
        val file = FileUtil.multipartFileToFile(multipartFile)
        val fileWriter = FileWriter(file)
        val csvBeanWriter = CsvBeanWriter(fileWriter, delimiterSetting)

        // list to varargs
        csvBeanWriter.writeHeader(*formattedHeaders.toTypedArray())
        csvBeanWriter.close()

        return FileUtil.fileToMultipartFile(file, CSV_MIME_TYPE)
    }
}
