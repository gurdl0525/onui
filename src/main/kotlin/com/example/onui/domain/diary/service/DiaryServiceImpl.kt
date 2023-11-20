package com.example.onui.domain.diary.service

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.exception.DiaryNotFoundException
import com.example.onui.domain.diary.presentation.request.ChattingWithGPTRequest
import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.request.UpdateDiaryRequest
import com.example.onui.domain.diary.presentation.response.ChattingResponse
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.diary.repository.QDiaryRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.error.exception.PermissionDeniedException
import com.example.onui.infra.feign.gpt.GPTClient
import com.example.onui.infra.feign.gpt.dto.request.GPTQueryRequest
import com.example.onui.infra.feign.gpt.dto.request.Message
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class DiaryServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val userFacade: UserFacade,
    private val qDiaryRepository: QDiaryRepository,
    private val gptClient: GPTClient
) : DiaryService {

    private companion object {
        const val M_SET = "너의 이름은 오누이이고 직업은 상담사야.\n아래 리스트는 내가 선택한 감정이야.\n"
        const val M_SET2 = "\n내 감정을 분석하고 되도록 짧게 존댓말로 솔루션을 제공해줘."
    }

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

    override fun getDetailById(date: LocalDate): DiaryDetailResponse? = diaryRepository.findByUserAndYearAndMonthAndDay(
        userFacade.getCurrentUser(), date.year, date.monthValue, date.dayOfMonth
    )?.toDetailResponse()

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

    override fun chattingWithGPT(req: ChattingWithGPTRequest): ChattingResponse {
        val res = gptClient.getGPTQuery(
            GPTQueryRequest(arrayOf(Message(M_SET + req.tagList.toString() + M_SET2)))
        )

        return ChattingResponse(res.choices[0].message.content)
    }
}