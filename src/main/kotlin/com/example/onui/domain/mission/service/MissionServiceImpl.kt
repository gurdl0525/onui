package com.example.onui.domain.mission.service

import com.example.onui.domain.mission.entity.AssignMission
import com.example.onui.domain.mission.entity.Assigned
import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.MissionType
import com.example.onui.domain.mission.exception.AlreadyFinishedMissionException
import com.example.onui.domain.mission.exception.MissionNotFoundException
import com.example.onui.domain.mission.exception.TypeCoastMissMatchedMissionException
import com.example.onui.domain.mission.presentation.dto.request.CreateMissionRequest
import com.example.onui.domain.mission.presentation.dto.response.MissionListResponse
import com.example.onui.domain.mission.presentation.dto.response.MissionResponse
import com.example.onui.domain.mission.repository.AssignMissionRepository
import com.example.onui.domain.mission.repository.AssignedRepository
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MissionServiceImpl(
    private val userFacade: UserFacade,
    private val missionRepository: MissionRepository,
    private val assignMissionRepository: AssignMissionRepository,
    private val assignedRepository: AssignedRepository,
    private val userRepository: UserRepository
) : MissionService {

    @Transactional
    override fun createMission(req: CreateMissionRequest): MissionResponse {

        userFacade.getAdmin()

        if (req.missionType!! == MissionType.ASSIGN && req.coast == null) throw TypeCoastMissMatchedMissionException

        val mission = missionRepository.save(
            Mission(
                req.name!!,
                req.goal!!,
                req.message!!,
                req.missionType
            )
        )

        val coast = req.coast?.let {
            return@let assignMissionRepository.save(AssignMission(mission, it)).coast
        }

        return mission.toResponse(coast, false)
    }

    override fun getMissions() = getAllMission(userFacade.getCurrentUser())

    @Transactional
    override fun complete(missionId: UUID): MissionListResponse {

        val user = userFacade.getCurrentUser()

        val mission = missionRepository.findByIdOrNull(missionId)
            ?: throw MissionNotFoundException

        val reward: Long = when (mission.missionType) {
            MissionType.ESSENTIAL -> 5
            else -> 10
        }

        val assigned = assignedRepository.findByIdOrNull(Assigned.IdClass(mission.id, user.id))
            ?: throw MissionNotFoundException

        if (assigned.isFinished) throw AlreadyFinishedMissionException

        userRepository.save(
            User(
                user.sub,
                user.name,
                user.profileTheme,
                user.theme,
                user.rice + reward,
                user.id,
                user.role,
                user.onFiltering
            )
        )

        assignedRepository.save(
            Assigned(
                assigned.user,
                assigned.mission,
                true
            )
        )

        return getAllMission(user)
    }

    private fun getAllMission(user: User) = MissionListResponse(
        assignedRepository.findAllByUser(user).map {
            it.mission.toResponse(it.mission.assignMission?.coast, it.isFinished)
        }.toMutableList()
    )
}