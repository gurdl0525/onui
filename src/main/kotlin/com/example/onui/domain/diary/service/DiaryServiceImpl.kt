package com.example.onui.domain.diary.service

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.exception.AlreadyWroteDiaryException
import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.error.exception.PermissionDeniedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val userFacade: UserFacade
): DiaryService {

    @Transactional
    override fun createDiary(req: CreateDiaryRequest): DiaryDetailResponse {

        val user = userFacade.getCurrentUser()
        val now = LocalDate.now()

        if (diaryRepository.existsByUserAndYearAndMonth(user, now.year, now.monthValue)) throw AlreadyWroteDiaryException

        val diary = diaryRepository.save(Diary(
            user,
            req.text,
            req.mood!!,
            now
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
}