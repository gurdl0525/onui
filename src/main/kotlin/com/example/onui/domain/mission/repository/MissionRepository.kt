package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.MissionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MissionRepository : JpaRepository<Mission, UUID?> {

    fun existsByName(name: String): Boolean

    fun findAllByMissionType(missionType: MissionType): MutableList<Mission>
}