package com.example.onui.domain.timeline.service

import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.util.*

interface TimeLineService {

    fun post(id: UUID): DiaryDetailResponse

    fun searchByDate(idx: Int, size: Int, date: LocalDate): Page<DiaryDetailResponse>
}
