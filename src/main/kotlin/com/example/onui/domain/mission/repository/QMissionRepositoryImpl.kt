package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.QMission.mission
import com.example.onui.domain.mission.entity.QAssignMission.assignMission
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service

@Service
class QMissionRepositoryImpl(private val queryFactory: JPAQueryFactory): QMissionRepository {

    override fun findAssignByCoast(coast: Int): List<Mission> = queryFactory
        .select(assignMission)
        .from(assignMission)
        .where(
            assignMission.coast.eq(coast.plus(1))
                .or(assignMission.coast.eq(coast.minus(1))
                    .or(assignMission.coast.eq(coast)))
        ).fetch()
}