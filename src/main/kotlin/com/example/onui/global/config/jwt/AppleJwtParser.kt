package com.example.onui.global.config.jwt

import com.example.onui.global.config.error.exception.InvalidTokenException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils


@Component
class AppleJwtParser(
    private val objectMapper: ObjectMapper
) {

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "\\."
        private const val HEADER_INDEX = 0
    }

    fun parseHeaders(identityToken: String): MutableMap<*, *> {
        return try {

            val encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER.toRegex())[HEADER_INDEX]
            objectMapper.readValue(String(Base64Utils.decodeFromUrlSafeString(encodedHeader)), MutableMap::class.java)

        } catch (e: JsonProcessingException) {
            throw InvalidTokenException

        } catch (e: ArrayIndexOutOfBoundsException) {
            throw InvalidTokenException
        }
    }
}