package com.example.onui.infra.fcm

import com.example.onui.global.env.FCMProperty
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class NotificationServiceImpl(
    private val fcmProperty: FCMProperty
) : NotificationService {

    private companion object {
        val logger = KotlinLogging.logger {  }
    }

    @PostConstruct
    fun init() {
        try {
            val options = FirebaseOptions.builder()
                .setCredentials(
                    GoogleCredentials
                        .fromStream(ClassPathResource(fcmProperty.path).getInputStream())
                        .createScoped(listOf(fcmProperty.scope))
                )
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            logger.error{ e.message }
            throw RuntimeException(e.message)
        }
    }

    // 알림 보내기
    override fun sendByTokenList(tokenList: MutableSet<String>, title: String, body: String) {

        val messages: MutableList<Message> = tokenList.stream().map {
            Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setToken(it)
                .build()
        }.toList()

        FirebaseMessaging.getInstance().sendAll(messages)
    }
}
