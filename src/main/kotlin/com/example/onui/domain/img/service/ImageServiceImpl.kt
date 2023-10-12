package com.example.onui.domain.img.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import com.example.onui.domain.img.entity.FileType
import com.example.onui.domain.img.exception.InvalidFileExtensionException
import com.example.onui.domain.img.presentation.dto.response.FileUrlResponse
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.s3.env.S3Property
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.UUID

@Service
class ImageServiceImpl(
    private val s3Client: AmazonS3Client,
    private val s3Property: S3Property,
    private val userFacade: UserFacade
): ImageService {

    private companion object {
        const val URL_PREFIX = "https://%s.s3.%s.amazonaws.com/%s"
    }

    override fun upload(file: MultipartFile) = uploadFile(file, userFacade.getCurrentUser().sub)

    private fun uploadFile(file: MultipartFile, sub: String): FileUrlResponse {

        val bytes: ByteArray = IOUtils.toByteArray(file.inputStream)

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = bytes.size.toLong()
        }

        var fileName: String = file.originalFilename ?: file.name


        val ext = fileName.split('.').last()

        try {
            FileType.values().first { it.extension == ext }
        } catch (e: NoSuchElementException) {
            throw InvalidFileExtensionException
        }

        fileName = s3Property.dir + "$sub/" + fileName

        val putObjectRequest = PutObjectRequest(
            s3Property.bucket,
            fileName,
            ByteArrayInputStream(bytes),
            objectMetadata,
        )

        s3Client.putObject(putObjectRequest)

        return FileUrlResponse(
            URL_PREFIX.format(
                s3Property.bucket,
                s3Property.region,
                fileName
            )
        )
    }
}