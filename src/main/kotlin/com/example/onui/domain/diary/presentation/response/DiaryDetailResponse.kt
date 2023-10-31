package com.example.onui.domain.diary.presentation.response

import com.example.onui.domain.diary.entity.Mood
import java.time.LocalDate
import java.util.UUID

data class DiaryDetailResponse(
    val id: UUID,
    val title: String,
    val content: String,
    val mood: Mood,
    val tag: MutableList<String>,
    val createdAt: LocalDate,
    val image: String?
)
