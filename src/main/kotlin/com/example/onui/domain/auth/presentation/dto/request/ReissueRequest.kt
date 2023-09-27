package com.example.onui.domain.auth.presentation.dto.request

import javax.validation.constraints.NotBlank

data class ReissueRequest (

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val refreshToken: String?
)