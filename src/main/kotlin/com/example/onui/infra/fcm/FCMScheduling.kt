package com.example.onui.infra.fcm

import com.example.onui.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FCMScheduling(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository
) {

    @Transactional
    @Scheduled(cron = "0 20 8 * * ?", zone = "Asia/Seoul")
    fun essentailHost() {
        userRepository.findAll().parallelStream().peek {
            notificationService.sendByTokenList(
                it.deviceToken, "감정 기록하실 때가 됐어요!", "사용자님 오늘도 오누이에 감정을 기록해보아요!!"
            )
        }
    }
}