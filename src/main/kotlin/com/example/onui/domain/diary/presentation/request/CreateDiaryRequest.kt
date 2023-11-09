package com.example.onui.domain.diary.presentation.request

import com.example.onui.domain.diary.entity.Mood
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateDiaryRequest(

    val content: String?,

    @field:NotNull(message = "null일 수 없습니다.")
    val mood: Mood?,

    @field:NotEmpty
    @field:NotNull
    val tagList: MutableList<String>?,

    val image: String?
)
