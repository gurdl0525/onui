package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MissionRepository<T: Mission>: JpaRepository<T, UUID?> {
}