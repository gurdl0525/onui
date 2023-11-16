package com.example.onui.domain.mission.presentation.dto.request

import java.util.*
import javax.validation.constraints.NotNull

data class CompleteMissionRequest(

    @field:NotNull(message = "null일 수 없습니다.")
    val missionId: UUID?
)
