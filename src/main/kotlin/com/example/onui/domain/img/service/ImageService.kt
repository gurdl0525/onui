package com.example.onui.domain.img.service

import com.example.onui.domain.img.presentation.dto.response.FileUrlResponse
import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun upload(file: MultipartFile): FileUrlResponse
}
