package com.example.onui.domain.diary.presentation

import com.example.onui.domain.diary.presentation.request.CreateDiaryRequest
import com.example.onui.domain.diary.presentation.request.UpdateDiaryRequest
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryListResponse
import com.example.onui.domain.diary.service.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
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
        @RequestParam("id", required = true)
        id: UUID
    ): DiaryDetailResponse = diaryService.getDetailById(id)

    @PostMapping
    fun updateDiary(
        @RequestBody @Valid
        req: UpdateDiaryRequest
    ) : DiaryDetailResponse = diaryService.update(req)
}