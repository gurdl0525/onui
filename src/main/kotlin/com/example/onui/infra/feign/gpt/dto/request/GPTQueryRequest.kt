package com.example.onui.infra.feign.gpt.dto.request

data class GPTQueryRequest(

    val prompt: String,
    val model: String = "gpt-4-1106-preview",
    val maxTokens: Int = 1000,
    val temperature: Int = 2
)