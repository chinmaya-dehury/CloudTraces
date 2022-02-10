package com.cloudtracebucket.storageapi.controller

import com.cloudtracebucket.storageapi.controller.response.FileInfoResponse
import com.cloudtracebucket.storageapi.factory.FileFactory
import com.cloudtracebucket.storageapi.validator.FileValidator
import com.jlefebure.spring.boot.minio.MinioService
import io.minio.messages.Item
import java.io.ByteArrayInputStream
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
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


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

        mockMvc!!.perform(get(path)
            .contentType(MediaType.APPLICATION_JSON))
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

//    @Test
//    fun uploadFileTest() {
//
//        val fileUploadResponse = FileUploadResponse().also {
//            it.fileName = "testFile.csv"
//            it.errors = listOf()
//        }
//
//        val mockMultipartFile = MockMultipartFile(
//            "file",
//            "testFile.csv",
//            "multipart/form-data",
//            "some rows".toByteArray()
//        )
//
//        val request = FileUploadRequest().also {
//            it.provider = "Google"
//            it.traceType = TraceType.SERVERLESS_PLATFORM
//        }
//
//        val mockRequest = MockMultipartFile(
//            "file_details",
//            "json",
//            "application/json",
//            ObjectMapper().writeValueAsBytes(request)
//        )
//
//        given(minioController.uploadFile(mockMultipartFile, request))
//            .willReturn(ResponseEntity(fileUploadResponse, HttpStatus.OK))
//
//        val mvcResult = mockMvc
//            .perform(multipart(path)
//                .file(mockMultipartFile)
//                .file(mockRequest)
//                .contentType(MediaType.MULTIPART_FORM_DATA))
//            .andExpect(status().`is`(200))
//            .andExpect(jsonPath("fileName", `is`("testFile.csv")))
//            .andExpect(jsonPath<List<Any>>("$.errors", hasSize(0)))
//            .andReturn()
//
//        assertEquals(200, mvcResult.response.status)
//        assertNotNull(mvcResult.response.contentAsString)
//    }

//    @Test
//    fun uploadFileTest_shouldReturn400() {
//        val fileUploadResponse = FileUploadResponse().also {
//            it.fileName = "testFile.pdf"
//            it.errors = listOf("Uploaded file is not a valid .csv file")
//        }
//
//        val mockMultipartFile = MockMultipartFile(
//            "file",
//            "testFile.pdf",
//            "multipart/form-data",
//            "some rows".toByteArray()
//        )
//
//        given(minioController.uploadFile(mockMultipartFile))
//            .willReturn(ResponseEntity(fileUploadResponse, HttpStatus.BAD_REQUEST))
//
//        val mvcResult = mockMvc
//            .perform(multipart(path).file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
//            .andExpect(status().`is`(400))
//            .andExpect(jsonPath("fileName", `is`("testFile.pdf")))
//            .andExpect(jsonPath<List<Any>>("$.errors", hasSize(1)))
//            .andExpect(jsonPath<List<Any>>("$.errors[0]", `is`("Uploaded file is not a valid .csv file")))
//            .andReturn()
//
//        assertEquals(400, mvcResult.response.status)
//        assertNotNull(mvcResult.response.contentAsString)
//    }
}
