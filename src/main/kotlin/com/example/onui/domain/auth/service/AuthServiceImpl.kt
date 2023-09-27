package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.global.config.jwt.TokenProvider
import com.example.onui.global.config.jwt.env.TokenProperty
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthServiceImpl(
    private val tokenProvider: TokenProvider,
    private val tokenProperty: TokenProperty
): AuthService {

    override fun reissue(refreshToken: String): TokenResponse {

        val (access, refresh) = tokenProvider.reissue(refreshToken)

        return TokenResponse (
            access,
            LocalDateTime.now().plusSeconds(tokenProperty.accessExp),
            refresh,
            LocalDateTime.now().plusSeconds(tokenProperty.refreshExp)
        )
    }
}