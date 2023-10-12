package com.example.onui.domain.timeline.service

import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import org.springframework.data.domain.Page
import java.time.DayOfWeek
import java.util.*

interface TimeLineService {

    fun post(id: UUID): TimelineResponse

    fun searchByDayOfWeek(idx: Int, size: Int, dayOfWeek: DayOfWeek): Page<TimelineResponse>
}
