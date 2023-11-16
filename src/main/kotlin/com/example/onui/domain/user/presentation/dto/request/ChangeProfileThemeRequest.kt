package com.example.onui.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank

data class ChangeProfileThemeRequest(

    @field:NotBlank(message = "profile_theme가 null일 수 없습니다.")
    val profileTheme: String?
)
