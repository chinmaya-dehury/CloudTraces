package com.cloudtracebucket.storageapi.controller.request

import org.springframework.web.multipart.MultipartFile

data class FileUploadRequest (val file: MultipartFile)
