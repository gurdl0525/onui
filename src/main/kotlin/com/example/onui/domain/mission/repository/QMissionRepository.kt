package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission

interface QMissionRepository {

    fun findAssignByCoast(coast: Int): List<Mission>
}