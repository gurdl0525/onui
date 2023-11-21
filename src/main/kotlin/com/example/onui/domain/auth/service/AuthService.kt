package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse

interface AuthService {

    fun reissue(refreshToken: String): TokenResponse

    fun signOut()

    fun applyDeviceToken(token: String)
}