package com.example.onui.domain.mission.presentation

import com.example.onui.domain.mission.presentation.dto.request.CompleteMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionListResponse
import com.example.onui.domain.mission.service.MissionService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    @GetMapping
    fun getMissions(): MissionListResponse = missionService.getMissions()

    @PostMapping
    fun complete(
        @RequestBody @Valid
        req: CompleteMissionRequest
    ): MissionListResponse = missionService.complete(req.missionId!!)
}