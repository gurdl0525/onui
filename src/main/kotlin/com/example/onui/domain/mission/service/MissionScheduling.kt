package com.example.onui.domain.mission.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionScheduling(
) {

//    @Transactional
//    @Scheduled(cron = "0 30 6 * * ?", zone = "Asia/Seoul")
//    fun assigningMission() {
//
//        val randomMissions = randomMissionRepository.findAll()
//
//        val generalMissions = generalMissionRepository.findAll()
//
//        userRepository.findAll()
//            .parallelStream()
//            .forEach {
//                val date = LocalDate.now()
//
//                val diaries =
//                    diaryRepository.findAllByUserAndYearAndMonthOrderByCreatedAtAsc(it, date.year, date.monthValue)
//
//                val assignMission: Mission = if (diaries.isNullOrEmpty()) {
//
//                    qDiaryRepository.findThreeDayAgoByUser(it)?.let { diary ->
//
//                        qMissionRepository.findAssignByCoast(diary.mood.coast).random()
//
//                    } ?: randomMissions.random()
//
//                } else randomMissions.random()
//
//                var randomMission = randomMissions.random()
//
//                while (randomMission == assignMission) {
//                    randomMission = randomMissions.random()
//                }
//
//                assignedRepository.saveAll(
//                    listOf(
//                        Assigned(it, generalMissions.random()),
//                        Assigned(it, assignMission),
//                        Assigned(it, randomMission)
//                    )
//                )
//            }
//    }
//
//    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
//    fun disAssigning() {
//        assignedRepository.deleteAll()
//    }

}