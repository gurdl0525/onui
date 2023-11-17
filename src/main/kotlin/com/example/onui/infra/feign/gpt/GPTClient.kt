package com.example.onui.infra.feign.gpt

import com.example.onui.global.config.feign.GPTFeignConfig
import com.example.onui.infra.feign.gpt.dto.request.GPTQueryRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "GPTClient",
    url = "https://api.openai.com/v1/chat/completions",
    configuration = [GPTFeignConfig::class]
)
interface GPTClient {

    @PostMapping(produces = ["application/json"])
    fun getGPTQuery(@RequestBody req: GPTQueryRequest): Map<*, *>
}