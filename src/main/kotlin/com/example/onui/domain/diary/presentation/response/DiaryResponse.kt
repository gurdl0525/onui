package com.example.onui.domain.diary.presentation.response

import com.example.onui.domain.diary.entity.Mood
import java.time.LocalDate
import java.util.UUID

data class DiaryResponse(
    val id: UUID,
    var mood: Mood,
    val createdAt: LocalDate
)
