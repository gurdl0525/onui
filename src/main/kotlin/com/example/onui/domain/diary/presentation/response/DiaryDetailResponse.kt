package com.example.onui.domain.diary.presentation.response

import com.example.onui.domain.diary.entity.Mood
import java.time.LocalDate
import java.util.*

data class DiaryDetailResponse(
    val id: UUID,
    val content: String,
    val mood: Mood,
    val tagList: MutableList<String>,
    val createdAt: LocalDate,
    val image: String?
)
