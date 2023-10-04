package com.example.onui.domain.diary.presentation.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class CreateDiaryRequest(

    val text: String?,

    @field:NotNull(message = "null일 수 없습니다.")
    @field:Min(1, message = "1보다 작을 수 없습니다.")
    @field:Max(5, message = "5보다 클 수 없습니다.")
    val mood: Int?,
)
