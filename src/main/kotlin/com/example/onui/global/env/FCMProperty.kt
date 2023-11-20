package com.example.onui.global.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("fcm.key")
data class FCMProperty(
    val path: String,
    val scope: String
)
