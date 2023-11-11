package com.example.onui.domain.auth.presentation

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.auth.service.AppleAuthService
import com.example.onui.domain.auth.service.AuthService
import com.example.onui.domain.auth.service.GoogleAuthService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/auth")
class AuthController(
    private val googleAuthService: GoogleAuthService,
    private val authService: AuthService,
    private val appleAuthService: AppleAuthService
) {

    @PostMapping("/google")
    fun oauthSignIn(
        @RequestParam(name = "token", required = true)
        token: String
    ): TokenResponse = googleAuthService.oauthGoogleSignIn(token)

    @PutMapping("/re-issue")
    fun reissue(
        @RequestParam("token", required = true)
        token: String
    ): TokenResponse = authService.reissue(token)

    @PostMapping("/apple")
    fun oauthSignInWithApple(
        @RequestParam(name = "token", required = true)
        token: String
    ) = appleAuthService.signUp(token)
}