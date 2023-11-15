package com.example.onui.domain.timeline.presentation.dto.response

import com.example.onui.domain.diary.entity.Mood
import java.time.LocalDate
import java.util.*

data class TimelineResponse(
    val id: UUID,
    val content: String?,
    val mood: Mood,
    val tagList: MutableList<String>,
    val image: String?,
    val writer: String,
    val commentCount: Int,
    val date: LocalDate
)
