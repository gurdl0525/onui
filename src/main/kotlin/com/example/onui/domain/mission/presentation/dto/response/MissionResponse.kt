package com.example.onui.domain.mission.presentation.dto.response

import com.example.onui.domain.mission.entity.MissionType
import java.util.*

data class MissionResponse(

    val id: UUID,

    val name: String,

    val goal: String,

    val message: String,

    val missionType: MissionType,

    val coast: Int?,

    val isFinished: Boolean
)