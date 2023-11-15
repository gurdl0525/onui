package com.example.onui.domain.user.presentation.dto.request

import javax.validation.constraints.NotNull

data class ChangeFilterRequest(

    @field:NotNull(message = "null일 수 없습니다.")
    val onFiltering: Boolean
)
