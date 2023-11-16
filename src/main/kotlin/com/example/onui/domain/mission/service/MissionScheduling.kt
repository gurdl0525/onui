package com.example.onui.domain.mission.service

import com.example.onui.domain.mission.entity.MissionType
import com.example.onui.domain.mission.repository.AssignedRepository
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionScheduling(
    private val missionRepository: MissionRepository,
    private val userRepository: UserRepository,
    private val assignedRepository: AssignedRepository,
    private val missionService: MissionService
) {

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    fun assigningMission() {

        assignedRepository.deleteAll()

        val randomMissions = missionRepository.findAllByMissionType(MissionType.RANDOM)

        userRepository.findAll().parallelStream().forEach { missionService.assignMission(it, randomMissions) }
    }
}