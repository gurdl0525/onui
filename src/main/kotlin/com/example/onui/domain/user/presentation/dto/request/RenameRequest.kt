package com.example.onui.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank

data class RenameRequest(

    @field:NotBlank(message = "null일 수 없습니다.")
    val name: String?
)
