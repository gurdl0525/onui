package com.example.onui.domain.diary.presentation.response

import java.time.LocalDate
import java.util.UUID

data class DiaryDetailResponse(
    val id: UUID,
    val text: String?,
    val mood: Int,
    val createdAt: LocalDate
)
