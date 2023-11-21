package com.example.onui.domain.auth.presentation

import com.example.onui.domain.auth.presentation.dto.request.OAuthSignInWithAndroidRequest
import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.auth.service.AppleAuthService
import com.example.onui.domain.auth.service.AuthService
import com.example.onui.domain.auth.service.GoogleAuthService
import com.example.onui.global.env.TokenProperty
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping("/auth")
class AuthController(
    private val googleAuthService: GoogleAuthService,
    private val authService: AuthService,
    private val appleAuthService: AppleAuthService,
    private val jwtProperty: TokenProperty
) {

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.CREATED)
    fun oauthSignIn(
        @RequestParam(name = "token", required = true)
        token: String
    ): TokenResponse = googleAuthService.oauthGoogleSignIn(token)

    @PostMapping("/google/v2")
    @ResponseStatus(HttpStatus.CREATED)
    fun oauthSignInWithAndroid(
        @RequestBody @Valid
        req: OAuthSignInWithAndroidRequest
    ): TokenResponse = googleAuthService.oauthGoogleSignInWith(req)

    @PutMapping("/re-issue")
    fun reissue(
        @RequestParam("token", required = true)
        token: String
    ): TokenResponse = authService.reissue(token)

    @PostMapping("/apple")
    @ResponseStatus(HttpStatus.CREATED)
    fun oauthSignInWithApple(
        @RequestParam(name = "token", required = true)
        token: String
    ) = appleAuthService.signUp(token)

    @GetMapping("/test")
    fun testToken(): String = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
        .setSubject("111899969632443851953")
        .setIssuedAt(Date())
        .setExpiration(Date(Date().time.plus(2592000000)))
        .compact()

    @DeleteMapping
    fun signOut() { authService.signOut() }

    @PostMapping("/device")
    fun applyDevieceToken(
        @RequestParam(name = "token", required = true)
        @Valid @NotBlank
        token: String
    ) { authService.applyDeviceToken(token) }
}