package com.example.onui.domain.timeline.presentation.dto.response

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.UUID

data class TimelineResponse (
    val id: UUID,
    val title: String,
    val content: String,
    val mood: Int,
    val tag: MutableList<String>,
    val dayOfWeek: DayOfWeek,
    val createdAt: LocalDateTime,
    val isUpdated: Boolean
)