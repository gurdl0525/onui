package com.example.onui.domain.auth.presentation.dto.request

data class AppleTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val code: String,
    val redirectUri: String,
    val grantType: String = "authorization_code"
)
