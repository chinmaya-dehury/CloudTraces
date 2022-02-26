package com.cloudtracebucket.storageapi.utils

import com.cloudtracebucket.storageapi.exception.UtilException
import com.cloudtracebucket.storageapi.pojo.enums.CsvStandardDelimiter
import com.cloudtracebucket.storageapi.utils.FileUtil.CSV_MIME_TYPE
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import org.apache.commons.io.FileUtils
import org.springframework.web.multipart.MultipartFile
import org.supercsv.io.CsvBeanReader
import org.supercsv.prefs.CsvPreference

object CsvUtil {

    fun getCsvHeaders(file: MultipartFile, delimiter: CsvStandardDelimiter): List<String> {
        val delimiterSettings = getDelimiterSetting(delimiter)
        val beanReader = CsvBeanReader(InputStreamReader(file.inputStream), delimiterSettings.first)

        return beanReader.getHeader(true)
            .map { formatHeader(it) }
    }

    fun getDelimiterSetting(delimiter: CsvStandardDelimiter): Pair<CsvPreference, String> {
        return when (delimiter) {
            CsvStandardDelimiter.COMMA_SEPARATED -> Pair(CsvPreference.STANDARD_PREFERENCE, ",")
            CsvStandardDelimiter.SEMICOLON_SEPARATED -> Pair(CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE, ";")
            CsvStandardDelimiter.TAB_SEPARATED -> Pair(CsvPreference.TAB_PREFERENCE, "\t")
            CsvStandardDelimiter.SPACE_SEPARATED -> Pair(
                CsvPreference.Builder('"', ' '.code, "\n").build(),
                "\\s"
            )
            CsvStandardDelimiter.PIPE_SEPARATED -> Pair(
                CsvPreference.Builder('"', '|'.code, "\n").build(),
                "|"
            )
        }
    }

    private fun formatHeader(header: String): String {
        return header
            .lowercase()
            .replace("[^a-zA-Z0-9]".toRegex(), " ")
            .trim()
            .replace("\\s+".toRegex(), "_")
    }

    fun replaceHeadersInFile(multipartFile: MultipartFile, headersAsString: String): MultipartFile {
        val file = FileUtil.multipartFileToFile(multipartFile)

        try {
            val lines = FileUtils.readLines(file, Charset.defaultCharset())
            lines[0] = headersAsString
            FileUtils.writeLines(file, lines, false)
        } catch (e: IOException) {
            throw UtilException("Failed to re-write headers in file ${file.name}", e)
        }

        return FileUtil.fileToMultipartFile(file, CSV_MIME_TYPE)
    }

    fun listToString(
        list: List<Any>,
        delimiter: CsvStandardDelimiter? = CsvStandardDelimiter.COMMA_SEPARATED,
    ): String {
        val delimiterSettings = getDelimiterSetting(delimiter!!)
        return list.joinToString(delimiterSettings.second)
    }
}
