package com.example.onui.domain.auth.presentation.dto.request

import javax.validation.constraints.NotBlank

data class OAuthSignInWithAndroidRequest (

    @field:NotBlank
    val sub: String?,

    @field:NotBlank
    val name: String?
)
