package com.example.onui.domain.mission.service

import com.example.onui.domain.mission.entity.AssignMission
import com.example.onui.domain.mission.entity.GeneralMission
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.domain.mission.repository.QMissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionServiceImpl(
    private val generalMissionRepository: MissionRepository<GeneralMission>,
    private val assignMissionRepository: MissionRepository<AssignMission>,
    private val qMissionRepository: QMissionRepository
) : MissionService {

    @Transactional
    override fun test(): Any {

        assignMissionRepository.save(
            AssignMission(
                "물 마시기",
                "오늘 하루 물 2L(생수병 한병) 마시기",
                -5,
                "우울한 기분을 물한잔으로 전환해봐요!, %s님! 우울한 하루의 시작을 물한잔만으로 활기찬 아침을 시작해봐요!"
            )
        )

        generalMissionRepository.save(
            GeneralMission(
                "오늘 하루 감정 기록하기",
                "오늘 하루도 꾸준히 기록해봐요!",
                "감정 기록 잊으신건 아니죠..? 저희 오누이를 찾아주세요!"
            )
        )

        return qMissionRepository.findAssignByCoast(-5)
    }
}