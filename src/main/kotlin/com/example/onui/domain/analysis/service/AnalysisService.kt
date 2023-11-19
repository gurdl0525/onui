package com.example.onui.domain.analysis.service

import com.example.onui.domain.analysis.presentation.dto.response.MonthlyChangeResponse
import com.example.onui.domain.analysis.presentation.dto.response.MoodeChangeResponse

interface AnalysisService {
    fun test()

    fun getMonthlyChange(): MonthlyChangeResponse

    fun getMoodeChange(): MoodeChangeResponse

}