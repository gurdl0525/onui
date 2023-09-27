package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.OauthLinkResponse
import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.auth.repository.RefreshTokenRepository
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.jwt.TokenProvider
import com.example.onui.global.config.jwt.env.TokenProperty
import com.example.onui.infra.feign.google.GoogleAuthClient
import com.example.onui.infra.feign.google.GoogleInfoClient
import com.example.onui.infra.feign.google.env.GoogleProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class GoogleAuthServiceImpl(
    private val googleAuth: GoogleAuthClient,
    private val googleInfo: GoogleInfoClient,
    private val googleProperty: GoogleProperty,
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val tokenProperty: TokenProperty,
    private val refreshTokenRepository: RefreshTokenRepository
): GoogleAuthService {

    private companion object {
        const val GOOGLE_URL = "%s" +
                "?client_id=%s" +
                "&redirect_uri=%s" +
                "&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
        const val ALT = "json"
        const val GRANT_TYPE = "authorization_code"
    }

    override fun getGoogleLoginLink() = OauthLinkResponse(
        GOOGLE_URL.format(
            googleProperty.baseUrl,
            googleProperty.clientId,
            googleProperty.redirectUrl
        )
    )

    @Transactional
    override fun oauthSignIn(code: String): TokenResponse {

        val accessToken = googleAuth.googleAuth(
            code,
            googleProperty.clientId,
            googleProperty.clientSecret,
            googleProperty.redirectUrl,
            GRANT_TYPE
        ).accessToken

        try {

            val response = googleInfo.googleInfo(ALT, accessToken)
            val email = response.email

            refreshTokenRepository.findByEmail(email)?.let {
                refreshTokenRepository.delete(it)
            }

            val (access, refresh) = tokenProvider.receiveToken(email)

            userRepository.findByEmail(email)
                ?: userRepository.save(User(
                    email = email,
                    name = "absdsa"
                ))

            return TokenResponse(
                access,
                LocalDateTime.now().plusSeconds(tokenProperty.accessExp / 1000),
                refresh,
                LocalDateTime.now().plusSeconds(tokenProperty.refreshExp / 1000)
            )
        }catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}