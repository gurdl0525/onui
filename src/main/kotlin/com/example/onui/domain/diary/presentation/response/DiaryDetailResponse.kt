package com.example.onui.domain.diary.presentation.response

import java.time.LocalDate
import java.util.UUID

data class DiaryDetailResponse(
    val id: UUID,
    val title: String,
    val content: String,
    val mood: Int,
    val tag: MutableList<String>,
    val createdAt: LocalDate,
    val image: String?
)
