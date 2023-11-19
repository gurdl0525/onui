package com.example.onui.domain.analysis.presentation.dto.response

import com.example.onui.domain.diary.presentation.response.DiaryResponse

data class MonthlyChangeResponse (
    val list: MutableList<DiaryResponse>,
    val message: String
) {
    companion object {
        fun of(list: MutableList<DiaryResponse>, message: String) = MonthlyChangeResponse(list, message)
    }
}
