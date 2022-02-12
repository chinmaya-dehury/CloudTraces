package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.request.FileUploadRequest
import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.controller.response.FileUploadResponse
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.pojo.enums.TraceType
import com.cloudtracebucket.storageapi.validator.FileValidator
import com.jlefebure.spring.boot.minio.MinioService
import io.minio.messages.Item
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.file.Path
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.`when`
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.web.multipart.MultipartFile

@RunWith(MockitoJUnitRunner::class)
@WebMvcTest(MinioController::class)
class MinioControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    lateinit var minioService: MinioService

    @MockBean
    lateinit var fileValidator: FileValidator

    @MockBean
    lateinit var fileFactory: FileFactory

    private val minioController: MinioController = mock(MinioController::class.java)

    private val path = "/files"

    @BeforeEach
    fun setUp() {
        openMocks(minioService)
        openMocks(fileValidator)
        openMocks(fileFactory)
    }

    @Test
    fun getFileListTest() {
        val files = listOf(
            FileInfoResponse().also {
                it.fileName = "test.csv"
                it.fileSize = 80L
            }
        )

        val mockedFileItemList = listOf(mock(Item::class.java))

        `when`(minioService.list()).thenReturn(mockedFileItemList)
        `when`(fileFactory.createFileListResponse(mockedFileItemList)).thenReturn(files)
        given(minioController.getListOfFiles()).willReturn(ResponseEntity(files, HttpStatus.OK))

        mockMvc!!.perform(
            get(path)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath<List<Any>>("$", hasSize(1)))
            .andExpect(jsonPath<List<Any>>("$[0].fileName", `is`(files[0].fileName)))
    }

    @Test
    fun getFileTest() {
        val testFilename = "test.csv"
        val mockInputStream = ByteArrayInputStream("TEST".toByteArray())

        `when`(minioService.get(Path.of(testFilename))).thenReturn(mockInputStream)

        val mvcResult = mockMvc!!
            .perform(get("$path/{fileName}", testFilename).contentType(MediaType.APPLICATION_OCTET_STREAM))
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
        val mockedFile = mock(MultipartFile::class.java)
        val request = FileUploadRequest().also {
            it.provider = "Google"
            it.traceType = TraceType.SERVERLESS_PLATFORM
        }

        val fileUploadResponse = FileUploadResponse().also {
            it.fileName = "testFile.csv"
            it.errors = listOf()
        }

        `when`(fileValidator.validateFileUploadRequest(mockedFile, request)).thenReturn(listOf())
        doNothing().`when`(minioService).upload(Path.of("test"), mock(InputStream::class.java), "test")
        `when`(fileFactory.createFileUploadResponse(mockedFile, listOf())).thenReturn(fileUploadResponse)

        val mockMultipartFile = MockMultipartFile(
            "file",
            "testFile.csv",
            "multipart/form-data",
            "some rows".toByteArray()
        )

        mockMvc!!.perform(multipart(path)
                .file(mockMultipartFile)
                .param("provider", request.provider)
                .param("traceType", request.traceType!!.name)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(status().isOk)
            .andReturn()
    }
}
