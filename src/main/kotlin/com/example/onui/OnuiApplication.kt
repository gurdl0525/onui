package com.example.onui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class OnuiApplication

fun main(args: Array<String>) {
    runApplication<OnuiApplication>(*args)
}
