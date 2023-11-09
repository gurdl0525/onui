package com.example.onui.global.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("spring.redis")
data class RedisProperty(
    val host: String,
    val port: Int
)
