package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Mission
import com.example.onui.domain.mission.entity.QAssignMission.assignMission
import com.example.onui.domain.mission.entity.QMission.mission
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QMissionRepositoryImpl(
    private val query: JPAQueryFactory
) : QMissionRepository {

    override fun findAssignByCoast(coast: Int): MutableList<Mission> {
        return query.selectFrom(mission)
            .innerJoin(mission.assignMission, assignMission)
            .where(assignMission.coast.eq(coast))
            .fetch().toMutableList()
    }
}