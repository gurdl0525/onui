package com.example.onui.domain.timeline.presentation.dto.response

import com.example.onui.domain.diary.entity.Mood
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.UUID

data class TimelineResponse (
    val id: UUID,
    val title: String,
    val content: String,
    val mood: Mood,
    val tag: MutableList<String>,
    val image: String?,
    val dayOfWeek: DayOfWeek,
    val createdAt: LocalDateTime,
    val isUpdated: Boolean
)