package com.example.onui.infra.feign.google.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("oauth.google")
@ConstructorBinding
data class GoogleProperty (
    val baseUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String
)
