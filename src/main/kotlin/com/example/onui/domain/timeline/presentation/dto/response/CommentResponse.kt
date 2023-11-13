package com.example.onui.domain.timeline.presentation.dto.response

import java.util.*

data class CommentResponse(
    val id: UUID,
    val timeline: UUID,
    val userTheme: String,
    val content: String
)
