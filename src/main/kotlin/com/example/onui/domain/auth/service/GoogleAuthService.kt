package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.OauthLinkResponse
import com.example.onui.domain.auth.presentation.dto.response.TokenResponse

interface GoogleAuthService {

    fun getGoogleLoginLink(): OauthLinkResponse

    fun oauthSignIn(code: String): TokenResponse
}