package com.example.onui.domain.auth.presentation

import com.example.onui.domain.auth.presentation.dto.request.ReissueRequest
import com.example.onui.domain.auth.presentation.dto.response.OauthLinkResponse
import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
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
    private val authService: AuthService
) {

    @GetMapping("/google/link")
    fun getGoogleClientId(): OauthLinkResponse = googleAuthService.getGoogleLoginLink()

    @GetMapping("/oauth/google/token")
    fun oauthSignIn(
        @RequestParam(name = "code", required = true)
        code: String
    ): TokenResponse = googleAuthService.oauthGoogleSignIn(code)

    @PutMapping("/token")
    fun reissue(
        @RequestBody @Valid
        req: ReissueRequest
    ): TokenResponse = authService.reissue(req.refreshToken!!)
}