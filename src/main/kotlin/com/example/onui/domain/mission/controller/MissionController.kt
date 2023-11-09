package com.example.onui.domain.mission.controller

import com.example.onui.domain.mission.service.MissionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    @GetMapping("/test")
    fun test() = missionService.test()

}