package com.example.onui.domain.mission.entity

import java.util.*
import javax.persistence.*

@Entity(name = "assign_mission")
class AssignMission(
    mission: Mission,
    coast: Int,
    id: UUID? = null
) {
    @Id
    @Column(name = "mission_id")
    var id: UUID? = id
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "mission_id", columnDefinition = "BINARY(16)")
    var mission: Mission = mission
        protected set

    @Column(name = "coast", nullable = false)
    var coast: Int = coast
        protected set
}