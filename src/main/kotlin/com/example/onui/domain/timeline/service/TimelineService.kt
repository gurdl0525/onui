package com.example.onui.domain.timeline.service

import com.example.onui.domain.timeline.presentation.dto.response.CommentListResponse
import com.example.onui.domain.timeline.presentation.dto.response.CommentResponse
import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.util.*

interface TimelineService {

    fun post(id: UUID): TimelineResponse

    fun searchByDate(idx: Int, size: Int, date: LocalDate): Page<TimelineResponse>

    fun comment(timelineId: UUID, comment: String): CommentResponse

    fun getComment(timelineId: UUID): CommentListResponse
}
