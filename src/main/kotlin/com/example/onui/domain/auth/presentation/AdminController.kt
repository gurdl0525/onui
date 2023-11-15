package com.example.onui.domain.auth.presentation

import com.example.onui.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val userService: UserService
) {

    @PostMapping("/theme")
    @ResponseStatus(HttpStatus.CREATED)
    fun postTheme(
        @RequestParam("id", required = true)
        id: String
    ) {
        userService.postTheme(id)
    }
}