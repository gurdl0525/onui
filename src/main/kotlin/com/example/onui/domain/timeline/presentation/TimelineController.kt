package com.example.onui.domain.timeline.presentation

import com.example.onui.domain.timeline.exception.InvalidDateFormatException
import com.example.onui.domain.timeline.service.TimeLineService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

@Validated
@RestController
@RequestMapping("/tl")
class TimelineController(
    private val timelineService: TimeLineService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTimeline(
        @RequestParam("id", required = true) id: UUID
    ) = timelineService.post(id)

    @GetMapping
    fun getByDate(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5,
        @RequestParam("date", required = true) date: String
    ) = try {
        timelineService.searchByDate(idx, size, LocalDate.parse(date))
    } catch (e: DateTimeParseException) {
        throw InvalidDateFormatException
    }
}