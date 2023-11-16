package com.example.onui.domain.mission.presentation

import com.example.onui.domain.mission.presentation.dto.response.MissionListResponse
import com.example.onui.domain.mission.service.MissionService
import mu.KotlinLogging
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    @GetMapping
    fun getMissions(): MissionListResponse = missionService.getMissions()
}