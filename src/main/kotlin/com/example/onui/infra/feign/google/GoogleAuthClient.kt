package com.example.onui.infra.feign.google

import com.example.onui.infra.feign.google.dto.OAuthTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "GoogleAuthClient", url = "https://oauth2.googleapis.com/token")
interface GoogleAuthClient {

    @PostMapping(headers = ["Content-Length=0"])
    fun googleAuth(
        @RequestParam(name = "code") code: String,
        @RequestParam(name = "clientId") clientId: String,
        @RequestParam(name = "clientSecret") clientSecret: String,
        @RequestParam(name = "redirectUri") redirectUri: String,
        @RequestParam(name = "grantType") grantType: String
    ): OAuthTokenResponse
}