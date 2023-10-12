package com.example.onui.domain.timeline.presentation

import com.example.onui.domain.timeline.service.TimeLineService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.util.UUID

@Validated
@RestController
@RequestMapping("/tl")
class TimelineController(
    private val timelineService: TimeLineService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTimeline(
        @RequestParam("id", required = true)
        id: UUID
    ) = timelineService.post(id)

    @GetMapping
    fun getByDayOfWeek(
        @RequestParam("idx", required = true)
        idx: Int = 0,
        @RequestParam("size", required = true)
        size: Int = 5,
        @RequestParam("day_of_week", required = true)
        dayOfWeek: DayOfWeek
    ) = timelineService.searchByDayOfWeek(idx, size, dayOfWeek)
}