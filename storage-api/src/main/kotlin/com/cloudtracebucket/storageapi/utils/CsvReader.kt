package com.cloudtracebucket.storageapi.utils

import java.io.FileReader
import org.supercsv.io.CsvBeanReader
import org.supercsv.prefs.CsvPreference

class CsvReader(
    private val csvFileName: String,
) {

    fun getCsvHeadersAsList() : List<String> {
        val beanReader = CsvBeanReader(FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE)
        val headers = beanReader.getHeader(true)

        return headers.asList()
    }
}