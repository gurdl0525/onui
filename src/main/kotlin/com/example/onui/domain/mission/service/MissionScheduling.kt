package com.example.onui.domain.mission.service

import com.example.onui.domain.diary.repository.QDiaryRepository
import com.example.onui.domain.mission.entity.Assigned
import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.MissionType
import com.example.onui.domain.mission.repository.AssignedRepository
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.domain.mission.repository.QMissionRepository
import com.example.onui.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class MissionScheduling(
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository,
    private val qDiaryRepository: QDiaryRepository,
    private val qMissionRepository: QMissionRepository,
    private val assignedRepository: AssignedRepository
) {

    @Transactional
    @Scheduled(cron = "0 41 15 * * ?", zone = "Asia/Seoul")
    fun assigningMission() {

        val date = LocalDate.now()

        val randomMissions = missionRepository.findAllByMissionType(MissionType.RANDOM)

        userRepository.findAll().parallelStream().forEach {

            val diaries = qDiaryRepository.findThreeDayAgoByUser(it)

            val assignMission: Mission = if (diaries.isNotEmpty() && diaries.count() > 1) {

                qMissionRepository.findAssignByCoast(diaries.sumOf { diary -> diary.mood.coast }).random()

            } else randomMissions.random()

            var randomMission = randomMissions.random()

            while (randomMission == assignMission) {
                randomMission = randomMissions.random()
            }

            assignedRepository.saveAll(
                listOf(
                    Assigned(it, missionRepository.findAllByMissionType(MissionType.ESSENTIAL).random()),
                    Assigned(it, assignMission),
                    Assigned(it, randomMission)
                )
            )
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    fun disAssigning() {
        assignedRepository.deleteAll()
    }

}