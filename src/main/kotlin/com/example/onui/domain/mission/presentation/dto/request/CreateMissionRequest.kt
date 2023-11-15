package com.example.onui.domain.mission.presentation.dto.request

import com.example.onui.domain.mission.entity.MissionType
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateMissionRequest(

    @field:NotBlank(message = "null일 수 없습니다.")
    val name: String?,

    @field:NotBlank(message = "null일 수 없습니다.")
    val goal: String?,

    @field:NotBlank(message = "null일 수 없습니다.")
    val message: String?,

    @field:NotNull(message = "null일 수 없습니다.")
    val missionType: MissionType?,

    @field:Min(-10, message = "-10 보다 커야합니다,")
    @field:Max(10, message = "10 보다 작야합니다,")
    val coast: Int?
)
