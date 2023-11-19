package com.example.onui.domain.analysis.presentation

import com.example.onui.domain.analysis.service.AnalysisService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/analysis")
class AnalysisController(private val analysisService: AnalysisService) {

    @GetMapping("/test")
    fun test() = analysisService.test()

    @GetMapping("/mood")
    fun getMoodChange() = analysisService.getMoodeChange()

    @GetMapping("/monthly")
    fun getMonthlyChange() = analysisService.getMonthlyChange()
}