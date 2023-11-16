package com.example.onui.domain.mission.service

import com.example.onui.domain.mission.presentation.dto.request.CreateMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionListResponse
import com.example.onui.domain.mission.presentation.dto.response.MissionResponse

interface MissionService {

    fun createMission(req: CreateMissionRequest): MissionResponse

    fun getMissions(): MissionListResponse
}