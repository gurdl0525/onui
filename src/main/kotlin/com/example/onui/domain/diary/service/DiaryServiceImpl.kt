package com.example.onui.domain.diary.service

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.exception.AlreadyWroteDiaryException
import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.request.UpdateDiaryRequest
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.timeline.entity.Timeline
import com.example.onui.domain.timeline.repository.TimelineRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val userFacade: UserFacade,
    private val timelineRepository: TimelineRepository
): DiaryService {

    @Transactional
    override fun createDiary(req: CreateDiaryRequest): DiaryDetailResponse {

        val user = userFacade.getCurrentUser()
        val now = LocalDateTime.now()

        if (diaryRepository.existsByUserAndYearAndMonth(user, now.year, now.monthValue)) throw AlreadyWroteDiaryException

        val diary = diaryRepository.save(Diary(
            user,
            req.title!!,
            req.content!!,
            req.mood!!,
            req.tagList!!,
            now,
            req.image
        ))

        user.diaryList.add(diary)

        return diary.toDetailResponse()
    }

    override fun getDiaryByMonth(year: Int, month: Int) = DiaryListResponse(
        diaryRepository.findAllByUserAndYearAndMonth(userFacade.getCurrentUser(), year, month)?.map {
            it.toResponse()
        }?.toMutableList()
    )

    override fun getDetailById(id: UUID): DiaryDetailResponse {
        val diary = diaryRepository.findByIdOrNull(id)
            ?: throw DiaryNotFoundException

        if (diary.user != userFacade.getCurrentUser()) throw PermissionDeniedException

        return diary.toDetailResponse()
    }

    @Transactional
    override fun update(req: UpdateDiaryRequest): DiaryDetailResponse {
        val user = userFacade.getCurrentUser()

        var diary = diaryRepository.findByIdOrNull(req.id)
            ?: throw DiaryNotFoundException

        if(diary.user != user) throw PermissionDeniedException

        diary = diaryRepository.save(Diary(
            diary.user,
            req.title!!,
            req.content!!,
            req.mood!!,
            req.tagList!!,
            diary.createdAt,
            req.image,
            diary.id
        ))

        timelineRepository.findByIdOrNull(diary.id)
            ?.run {
                timelineRepository.save(Timeline(
                    diary,
                    diary.createdAt,
                    id,
                    true,
                ))
            }

        return diary.toDetailResponse()
    }
}