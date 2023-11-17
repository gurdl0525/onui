package com.example.onui.global.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gpt")
data class GPTProperty(

    val secretKey: String
)
