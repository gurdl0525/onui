package com.example.onui.infra.fcm

import com.example.onui.global.env.FCMProperty
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
class NotificationServiceImpl(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationService {

    private companion object {
        val logger = KotlinLogging.logger { }
    }

    @Transactional
    override fun sendByTokenList(tokenList: MutableSet<String>, title: String, body: String) {

        val messages: List<Message> = tokenList.map {
            Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                .setToken(it)
                .build()
        }.toList()

        try {
            val response: BatchResponse = firebaseMessaging.sendAll(messages)


            if ((messages.size - response.successCount) > 0) {
                val responses: MutableList<SendResponse> = response.responses;
                val failedTokens: MutableList<String> = mutableListOf()

                var i = 0
                while (i < responses.size) {
                    if (!responses[i].isSuccessful) {
                        failedTokens.add(tokenList.elementAt(i))
                    }
                    i += 1
                }
                logger.error { "List of tokens are not valid FCM token : $failedTokens" }
            }
        } catch (e: FirebaseMessagingException) {
            logger.error { "cannot send to memberList push message. error info : ${e.message}" }
        } catch (e: Exception) {
            logger.error { e.localizedMessage }
        }
    }
}
