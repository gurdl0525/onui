package com.example.onui.infra.feign.google.dto

data class GoogleInfoResponse(
    val sub: String,
    val name: String,
    val givenName: String,
    val familyName: String?,
    val picture: String,
    val email: String,
    val emailVerified: Boolean,
    val locale: String
)