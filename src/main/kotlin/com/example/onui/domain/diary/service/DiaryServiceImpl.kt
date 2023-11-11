package com.example.onui.domain.diary.service

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.request.UpdateDiaryRequest
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.diary.repository.QDiaryRepository
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
    private val qDiaryRepository: QDiaryRepository
) : DiaryService {

    @Transactional
    override fun createDiary(req: CreateDiaryRequest): DiaryDetailResponse {

        val user = userFacade.getCurrentUser()
        val now = LocalDateTime.now()

        val diary =
            diaryRepository.findByUserAndYearAndMonthAndDay(user, now.year, now.monthValue, now.dayOfMonth)?.let {
                Diary(
                    user, req.content, req.mood!!, req.tagList!!, it.createdAt, req.image, it.id, it.isPosted
                )
            } ?: Diary(
                user, req.content, req.mood!!, req.tagList!!, now, req.image
            )

        diaryRepository.save(diary)

        return diary.toDetailResponse()
    }

    override fun getDiaryByMonth(year: Int, month: Int) = DiaryListResponse(
        diaryRepository.findAllByUserAndYearAndMonthOrderByCreatedAtAsc(userFacade.getCurrentUser(), year, month)?.map {
            it.toResponse()
        }?.toMutableList()
    )

    override fun getDetailById(id: UUID): DiaryDetailResponse {
        val diary = diaryRepository.findByIdOrNull(id) ?: throw DiaryNotFoundException

        if (diary.user != userFacade.getCurrentUser()) throw PermissionDeniedException

        return diary.toDetailResponse()
    }

    @Transactional
    override fun update(req: UpdateDiaryRequest): DiaryDetailResponse {
        val user = userFacade.getCurrentUser()

        val diary = diaryRepository.findByIdOrNull(req.id) ?: throw DiaryNotFoundException

        if (diary.user != user) throw PermissionDeniedException

        return diaryRepository.save(
            Diary(
                diary.user, req.content, req.mood!!, req.tagList!!, diary.createdAt, req.image, diary.id
            )
        ).toDetailResponse()
    }

    override fun getSevenDaysAgo(): DiaryListResponse {

        val diaries = qDiaryRepository.findSevenDayAgoByUser(userFacade.getCurrentUser())

        return DiaryListResponse(if (diaries.isEmpty()) null else diaries)
    }
}