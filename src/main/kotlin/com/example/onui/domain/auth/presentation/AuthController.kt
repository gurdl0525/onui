package com.example.onui.domain.auth.presentation

import com.example.onui.domain.auth.presentation.dto.request.ReissueRequest
import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.auth.service.AppleAuthService
import com.example.onui.domain.auth.service.AuthService
import com.example.onui.domain.auth.service.GoogleAuthService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/auth")
class AuthController(
    private val googleAuthService: GoogleAuthService,
    private val authService: AuthService,
    private val appleAuthService: AppleAuthService
) {

    @GetMapping("/oauth/google/token")
    fun oauthSignIn(
        @RequestParam(name = "token", required = true)
        token: String
    ): TokenResponse = googleAuthService.oauthGoogleSignIn(token)

    @PutMapping("/token")
    fun reissue(
        @RequestBody @Valid
        req: ReissueRequest
    ): TokenResponse = authService.reissue(req.refreshToken!!)

    @PostMapping("/oauth/apple/token")
    fun oauthSignInWithApple(
        @RequestParam(name = "code", required = true)
        code: String
    ) = appleAuthService.signUp(code)

    @GetMapping("/test")
    fun test() = appleAuthService.test()
}