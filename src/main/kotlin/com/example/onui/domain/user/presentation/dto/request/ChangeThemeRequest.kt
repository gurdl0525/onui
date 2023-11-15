package com.example.onui.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank

data class ChangeThemeRequest(

    @field:NotBlank(message = "theme가 null일 수 없습니다.")
    val theme: String?
)