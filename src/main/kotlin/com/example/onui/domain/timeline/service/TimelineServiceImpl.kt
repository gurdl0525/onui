package com.example.onui.domain.timeline.service

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.timeline.exception.AlreadyPostedTimeLineException
import com.example.onui.domain.timeline.repository.QTimelineRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.error.exception.PermissionDeniedException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class TimelineServiceImpl(
    private val userFacade: UserFacade,
    private val qTimelineRepository: QTimelineRepository,
    private val diaryRepository: DiaryRepository
) : TimeLineService {

    @Transactional
    override fun post(id: UUID): DiaryDetailResponse {
        val user = userFacade.getCurrentUser()

        val diary = diaryRepository.findByIdOrNull(id)
            ?: throw DiaryNotFoundException

        if (diary.user != user) throw PermissionDeniedException

        if (diaryRepository.existsByIdAndIsPosted(id, true)) throw AlreadyPostedTimeLineException

        return diaryRepository.save(
            Diary(
                diary.user,
                diary.content,
                diary.mood,
                diary.tagList,
                diary.createdAt,
                diary.image,
                diary.id,
                true
            )
        ).toDetailResponse()
    }

    override fun searchByDate(idx: Int, size: Int, date: LocalDate) = qTimelineRepository
        .findPageByDate(
            PageRequest.of(idx, size, Sort.by("diary.createdAt").descending()), date
        )
}