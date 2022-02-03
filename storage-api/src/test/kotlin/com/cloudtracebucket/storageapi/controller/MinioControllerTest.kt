package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.jlefebure.spring.boot.minio.MinioService
import java.net.URLConnection
import java.nio.file.Path
import java.time.ZonedDateTime
import javax.servlet.http.HttpServletResponse
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mock
import org.mockito.Mockito.*


@RunWith(SpringRunner::class)
@WebMvcTest(MinioController::class)
class MinioControllerTest @Autowired constructor(
    private val mvc: MockMvc
) {
    @MockBean
    lateinit var minioController: MinioController

    @Test
    fun getFileListTest() {
        val path = "/files"
        val files = listOf(
            FileInfoResponse().also {
                it.fileName = "test.txt"
                it.fileSize = 80L
                it.uploadTime = ZonedDateTime.now()
            }
        )

        given(minioController.getListOfFiles()).willReturn(ResponseEntity(files, HttpStatus.OK))

        mvc.perform(get(path).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<List<Any>>("$", hasSize(1)))
            .andExpect(jsonPath<List<Any>>("$[0].fileName", `is`(files[0].fileName)))
    }

    @Test
    fun getFileTest() {
        val testFilename = "test.csv"
        val path = "/files/$testFilename"
        val result = mvc.perform(get(path)
            .contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(status().`is`(200))
            .andReturn()

        result.response.contentType = "multipart/form-data"
        result.response.addHeader("Content-disposition", "attachment;filename=$testFilename")

        assertTrue(result.response.headerNames.contains("Content-disposition"))
        assertEquals("multipart/form-data", result.response.contentType)
        assertEquals("attachment;filename=$testFilename", result.response.getHeader("Content-disposition"))
        assertEquals(200, result.response.status)
    }
}
