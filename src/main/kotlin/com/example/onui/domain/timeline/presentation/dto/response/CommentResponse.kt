package com.example.onui.domain.timeline.presentation.dto.response

import java.util.*

data class CommentResponse(
    val id: UUID,
    val timeline: UUID,
    val user: UUID,
    val content: String
)
