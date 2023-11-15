package com.example.onui.domain.auth.presentation

import com.example.onui.domain.mission.presentation.dto.request.CreateMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionResponse
import com.example.onui.domain.mission.service.MissionService
import com.example.onui.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/admin")
@Validated
class AdminController(
    private val userService: UserService,
    private val missionService: MissionService
) {

    @PostMapping("/theme")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTheme(
        @RequestParam("id", required = true)
        id: String
    ) {
        userService.postTheme(id)
    }

    @PostMapping("/mission")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMission(
        @RequestBody @Valid
        req: CreateMissionRequest
    ): MissionResponse = missionService.createMission(req)
}