package com.example.onui.domain.diary.presentation.response

import java.time.LocalDate
import java.util.UUID

data class DiaryResponse(
    val id: UUID,
    var mood: Int,
    val createdAt: LocalDate
)
