package com.example.onui.domain.diary.presentation

import com.example.onui.domain.diary.presentation.request.ChattingWithGPTRequest
import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.request.UpdateDiaryRequest
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.service.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/diary")
class DiaryController(
    private val diaryService: DiaryService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody @Valid
        request: CreateDiaryRequest
    ) = diaryService.createDiary(request)

    @GetMapping
    fun getByMonth(
        @RequestParam("year", required = true)
        year: Int,
        @RequestParam("month", required = true)
        @Min(1) @Max(12)
        month: Int
    ): DiaryListResponse = diaryService.getDiaryByMonth(year, month)

    @GetMapping("/detail")
    fun getById(
        @RequestParam("date", required = true)
        date: String
    ): DiaryDetailResponse? = diaryService.getDetailById(LocalDate.parse(date))

    @PutMapping
    fun updateDiary(
        @RequestBody @Valid
        req: UpdateDiaryRequest
    ): DiaryDetailResponse = diaryService.update(req)

    @GetMapping("/ago")
    fun getSevenDaysAgo() = diaryService.getSevenDaysAgo()

    @GetMapping("/chatting")
    fun test(
        @RequestBody @Valid
        req: ChattingWithGPTRequest
    ) = diaryService.chattingWithGPT(req)
}