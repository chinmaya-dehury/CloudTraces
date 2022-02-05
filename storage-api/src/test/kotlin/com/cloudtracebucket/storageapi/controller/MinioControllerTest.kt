package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import java.time.ZonedDateTime
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.notNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@WebMvcTest(MinioController::class)
class MinioControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) {
    @MockBean
    lateinit var minioController: MinioController

    private val path = "/files"

    @Test
    fun getFileListTest() {
        val files = listOf(
            FileInfoResponse().also {
                it.fileName = "test.txt"
                it.fileSize = 80L
                it.uploadTime = ZonedDateTime.now()
            }
        )

        given(minioController.getListOfFiles()).willReturn(ResponseEntity(files, HttpStatus.OK))

        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath<List<Any>>("$", hasSize(1)))
            .andExpect(jsonPath<List<Any>>("$[0].fileName", `is`(files[0].fileName)))
    }

    @Test
    fun getFileTest() {
        val testFilename = "test.csv"
        val path = "/files/$testFilename"
        val mvcResult = mockMvc.perform(
            get(path)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
        )
            .andExpect(status().`is`(200))
            .andReturn()

        mvcResult.response.contentType = "multipart/form-data"
        mvcResult.response.addHeader("Content-disposition", "attachment;filename=$testFilename")

        assertTrue(mvcResult.response.headerNames.contains("Content-disposition"))
        assertEquals("multipart/form-data", mvcResult.response.contentType)
        assertEquals("attachment;filename=$testFilename", mvcResult.response.getHeader("Content-disposition"))
        assertEquals(200, mvcResult.response.status)
    }

    @Test
    fun uploadFileTest() {

        val fileUploadResponse = FileUploadResponse().also {
            it.fileName = "testFile.csv"
        }

        val mockMultipartFile = MockMultipartFile(
            "file",
            "testFile.csv",
            "multipart/form-data",
            "some rows".toByteArray()
        )

        given(minioController.uploadFile(mockMultipartFile))
            .willReturn(ResponseEntity(fileUploadResponse, HttpStatus.OK))

        val mvcResult = mockMvc
            .perform(multipart(path).file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("fileName", `is`("testFile.csv")))
            .andExpect(jsonPath<List<Any>>("$.errors", hasSize(0)))
            .andReturn()

        assertEquals(200, mvcResult.response.status)
        assertNotNull(mvcResult.response.contentAsString)
    }

    @Test
    fun uploadFileTest_shouldReturn400() {
        val fileUploadResponse = FileUploadResponse().also {
            it.fileName = "testFile.pdf"
            it.errors = listOf("Uploaded file is not a valid .csv file")
        }

        val mockMultipartFile = MockMultipartFile(
            "file",
            "testFile.pdf",
            "multipart/form-data",
            "some rows".toByteArray()
        )

        given(minioController.uploadFile(mockMultipartFile))
            .willReturn(ResponseEntity(fileUploadResponse, HttpStatus.BAD_REQUEST))

        val mvcResult = mockMvc
            .perform(multipart(path).file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().`is`(400))
            .andExpect(jsonPath("fileName", `is`("testFile.pdf")))
            .andExpect(jsonPath<List<Any>>("$.errors", hasSize(1)))
            .andExpect(jsonPath<List<Any>>("$.errors[0]", `is`("Uploaded file is not a valid .csv file")))
            .andReturn()

        assertEquals(400, mvcResult.response.status)
        assertNotNull(mvcResult.response.contentAsString)
    }
}
