package com.example.onui.global.config.feign

import com.example.onui.global.config.error.exception.FeignClientException
import com.example.onui.global.config.error.exception.ExpiredTokenException
import com.example.onui.global.config.error.exception.InvalidTokenException
import com.example.onui.global.config.error.exception.PermissionDeniedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging

class FeignClientErrorDecoder: ErrorDecoder {

    private companion object{
        val logger = KotlinLogging.logger{}
    }

    override fun decode(methodKey: String, response: Response): Exception {

        logger.error { response.status() }
        logger.error { response.body() }
        logger.error { response.reason() }

        if(response.status() >= 400) {
            when(response.status()){
                401 -> throw InvalidTokenException
                403 -> throw PermissionDeniedException
                419 -> throw ExpiredTokenException
                else -> throw FeignClientException
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }

}