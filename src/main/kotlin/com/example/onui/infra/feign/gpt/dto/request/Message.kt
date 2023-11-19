package com.example.onui.infra.feign.gpt.dto.request

data class Message (

    val content: String,
    val role: String = "user"
)
