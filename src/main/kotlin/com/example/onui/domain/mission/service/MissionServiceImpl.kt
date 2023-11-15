package com.example.onui.domain.mission.service

import com.example.onui.domain.mission.entity.AssignMission
import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.MissionType
import com.example.onui.domain.mission.exception.AlreadyCreatedMissionException
import com.example.onui.domain.mission.exception.TypeCoastMissMatchedMissionException
import com.example.onui.domain.mission.presentation.dto.request.CreateMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionResponse
import com.example.onui.domain.mission.repository.AssignMissionRepository
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionServiceImpl(
    private val userFacade: UserFacade,
    private val missionRepository: MissionRepository,
    private val assignMissionRepository: AssignMissionRepository
) : MissionService {

    @Transactional
    override fun createMission(req: CreateMissionRequest): MissionResponse {

        userFacade.getAdmin()

        if (req.missionType!! == MissionType.ASSIGN && req.coast == null) throw TypeCoastMissMatchedMissionException
        if (missionRepository.existsByName(req.name!!)) throw AlreadyCreatedMissionException
        
        val mission = missionRepository.save(
            Mission(
                req.name,
                req.goal!!,
                req.message!!,
                req.missionType
            )
        )

        val coast = req.coast?.let {
            return@let assignMissionRepository.save(AssignMission(mission, it)).coast
        }

        return mission.toResponse(coast)
    }
}