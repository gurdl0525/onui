package com.example.onui.domain.timeline.service

import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.timeline.entity.Timeline
import com.example.onui.domain.timeline.exception.AlreadyPostedTimeLineException
import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import com.example.onui.domain.timeline.repository.TimelineRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.error.exception.PermissionDeniedException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.util.*

@Service
@Transactional(readOnly = true)
class TimelineServiceImpl(
    private val userFacade: UserFacade,
    private val timelineRepository: TimelineRepository,
    private val diaryRepository: DiaryRepository
): TimeLineService {

    @Transactional
    override fun post(id: UUID): TimelineResponse {
        val user = userFacade.getCurrentUser()

        val diary = diaryRepository.findByIdOrNull(id)
            ?: throw DiaryNotFoundException

        if (diary.user != user) throw PermissionDeniedException

        if (timelineRepository.existsById(id)) throw AlreadyPostedTimeLineException

        return timelineRepository.save(Timeline(
            diary,
            diary.createdAt
        )).toResponse()
    }

    override fun searchByDayOfWeek(idx: Int, size: Int, dayOfWeek: DayOfWeek) = timelineRepository
        .findAllByDayOfWeek(
            dayOfWeek,
            PageRequest.of(idx, size, Sort.by("diary.createdAt").descending())
        ).map { it.toResponse() }
}