package com.example.onui.global.common.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/healthcheck")
    @ResponseStatus(HttpStatus.OK)
    fun healthCheck() = null
}