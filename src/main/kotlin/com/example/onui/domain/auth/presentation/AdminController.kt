package com.example.onui.domain.auth.presentation

import com.example.onui.domain.mission.presentation.dto.request.CreateMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionResponse
import com.example.onui.domain.mission.service.MissionService
import com.example.onui.domain.user.service.UserService
import com.example.onui.infra.fcm.FCMScheduling
import com.vane.badwordfiltering.BadWordFiltering
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/admin")
@Validated
class AdminController(
    private val userService: UserService,
    private val missionService: MissionService,
    private val badWordFiltering: BadWordFiltering,
    private val fcmScheduling: FCMScheduling
) {

    @PostMapping("/theme")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTheme(
        @RequestParam("id", required = true)
        id: String,
        @RequestParam("price", required = true)
        price: Long
    ) {
        userService.postTheme(id, price)
    }

    @PostMapping("/mission")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMission(
        @RequestBody @Valid
        req: CreateMissionRequest
    ): MissionResponse = missionService.createMission(req)

    @PostMapping("/badword")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBadWord(
        @RequestParam("text", required = true)
        text: String
    ) {
        badWordFiltering.add(text)
    }

    @PostMapping("/fcm")
    fun postFCM() { fcmScheduling.essentailHost() }
}