package com.example.onui.domain.timeline.presentation

import com.example.onui.domain.timeline.exception.InvalidDateFormatException
import com.example.onui.domain.timeline.service.TimelineService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@RestController
@RequestMapping("/tl")
class TimelineController(
    private val timelineService: TimelineService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTimeline(@RequestParam("id", required = true) id: UUID) = timelineService.post(id)

    @GetMapping
    fun getByDate(
        @RequestParam("idx", required = true) @NotNull idx: Int = 0,
        @RequestParam("size", required = true) @NotNull @Min(1) size: Int = 5,
        @RequestParam("date", required = true) @NotBlank date: String
    ) = try {
        timelineService.searchByDate(idx, size, LocalDate.parse(date))
    } catch (e: DateTimeParseException) {
        throw InvalidDateFormatException
    }
}