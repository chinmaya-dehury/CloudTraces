package com.cloudtracebucket.storageapi.utils

import com.cloudtracebucket.storageapi.pojo.enums.CsvStandardDelimiter
import java.io.InputStreamReader
import org.springframework.web.multipart.MultipartFile
import org.supercsv.io.CsvBeanReader
import org.supercsv.prefs.CsvPreference

object CsvReader {

    fun getCsvHeadersAsList(file: MultipartFile, delimiter: CsvStandardDelimiter) : String {
        val delimiterSetting = getDelimiterSetting(delimiter)
        val beanReader = CsvBeanReader(InputStreamReader(file.inputStream), delimiterSetting)
        val headers = beanReader.getHeader(true)

        return headers
            .asList()
            .joinToString(",") { header -> formatHeader(header) }
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
            .replace("\\s".toRegex(), "_")
    }
}
