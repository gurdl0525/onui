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

    @Suppress("unchecked_cast")
    fun parseHeaders(token: String): MutableMap<String?, String?> {
        return try {
            val encodedHeader: String = token.split(IDENTITY_TOKEN_VALUE_DELIMITER.toRegex())[HEADER_INDEX]
            val decodedHeader = String(Base64Utils.decodeFromUrlSafeString(encodedHeader))
            objectMapper.readValue(decodedHeader, MutableMap::class.java) as MutableMap<String?, String?>
        } catch (e: JsonProcessingException) {
            throw InvalidTokenException
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw InvalidTokenException
        }
    }
}