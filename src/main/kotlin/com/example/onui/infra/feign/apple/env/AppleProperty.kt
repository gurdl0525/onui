package com.example.onui.infra.feign.apple.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("oauth.apple")
@ConstructorBinding
data class AppleProperty (
    val iss: String,
    val clientId: String,
    val nonce: String
)