package com.example.onui.domain.timeline.repository

import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface QTimelineRepository {

    fun findPageByDate(pageable: Pageable, date: LocalDate): Page<TimelineResponse>
}
