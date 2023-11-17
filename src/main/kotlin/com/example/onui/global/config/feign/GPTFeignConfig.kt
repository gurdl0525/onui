package com.example.onui.global.config.feign

import com.example.onui.global.env.GPTProperty
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(FeignClientsConfiguration::class)
class GPTFeignConfig(private val gptProperty: GPTProperty) {

    @Bean
    fun gptRequestInterceptor(): RequestInterceptor =
        RequestInterceptor { it.header("Authorization", "Bearer ${gptProperty.secretKey}") }

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): FeignClientErrorDecoder = FeignClientErrorDecoder()
}