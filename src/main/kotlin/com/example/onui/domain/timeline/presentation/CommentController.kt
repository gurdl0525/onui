package com.example.onui.domain.timeline.presentation

import com.example.onui.domain.timeline.presentation.dto.request.CommentRequest
import com.example.onui.domain.timeline.service.TimelineService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/comment")
class CommentController(
    private val timelineService: TimelineService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
        @RequestParam("timeline_id", required = true)
        timelineId: UUID,
        @RequestBody @Valid
        req: CommentRequest
    ) = timelineService.comment(timelineId, req.comment)

    @GetMapping
    fun getComment(
        @RequestParam("timeline_id", required = true)
        timelineId: UUID
    ) = timelineService.getComment(timelineId)
}