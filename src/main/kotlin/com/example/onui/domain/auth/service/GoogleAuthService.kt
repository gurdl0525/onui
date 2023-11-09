package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse

interface GoogleAuthService {

    fun oauthGoogleSignIn(token: String): TokenResponse
}