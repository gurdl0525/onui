package com.example.onui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@ConfigurationPropertiesScan(basePackages = ["com.example.onui.global.env"])
@EnableScheduling
@SpringBootApplication
@EnableFeignClients(basePackageClasses = [OnuiApplication::class])
class OnuiApplication

fun main(args: Array<String>) {
    runApplication<OnuiApplication>(*args)
}
