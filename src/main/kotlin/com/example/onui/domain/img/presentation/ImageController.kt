package com.example.onui.domain.img.presentation

import com.example.onui.domain.img.service.ImageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/img")
class ImageController(
    private val imageService: ImageService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @RequestPart("file", required = true) file: MultipartFile,
    ) = imageService.upload(file)
}