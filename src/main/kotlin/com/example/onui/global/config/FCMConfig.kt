package com.example.onui.global.config

import com.example.onui.global.env.FCMProperty
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path


@Configuration
class FCMConfig(
    private val fcmProperty: FCMProperty
) {

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val rfToken: InputStream = Files.newInputStream(Paths.get(fcmProperty.path))
        var firebaseApp: FirebaseApp? = null
        val firebaseAppList = FirebaseApp.getApps()
        if (firebaseAppList != null && firebaseAppList.isNotEmpty()) {
            for (app in firebaseAppList) {
                if (app.name == FirebaseApp.DEFAULT_APP_NAME) {
                    firebaseApp = app
                }
            }
        } else {
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(rfToken).createScoped(fcmProperty.scope))
                .build()
            firebaseApp = FirebaseApp.initializeApp(options)
        }
        return FirebaseMessaging.getInstance(firebaseApp)
    }
}