package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.QMission.mission
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service

@Service
class QMissionRepositoryImpl(private val queryFactory: JPAQueryFactory): QMissionRepository {

    fun findByMinusCoast(coast: Int): List<Mission> {
        return queryFactory.select(mission)
            .from(mission)
            .where(mission.coast.eq(coast.plus(1)).or(mission.coast.eq(coast.minus(1))))
            .fetch()
    }
}