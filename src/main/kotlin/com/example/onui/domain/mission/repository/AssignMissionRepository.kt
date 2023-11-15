package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.AssignMission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AssignMissionRepository : JpaRepository<AssignMission, UUID?> {

}
