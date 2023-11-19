package com.example.onui.infra.feign.gpt.dto.response

data class Choice (
    val index: Int,
    val message: GPTMessage,
    val finishReason: String
)
