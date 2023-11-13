package com.example.onui.domain.user.presentation

import com.example.onui.domain.user.presentation.dto.request.RenameRequest
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import com.example.onui.domain.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PatchMapping("/rename")
    fun rename(
        @RequestBody @Valid
        request: RenameRequest
    ): UserProfileResponse = userService.rename(request.name!!)

    @GetMapping("/profile")
    fun getProfile(): UserProfileResponse = userService.getProfile()
}