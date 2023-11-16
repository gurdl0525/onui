package com.example.onui.domain.mission.entity

import com.example.onui.domain.mission.presentation.dto.response.MissionResponse
import java.util.*
import javax.persistence.*

@Entity(name = "mission")
class Mission(
    name: String,
    goal: String,
    message: String,
    missionType: MissionType,
    id: UUID? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "goal", nullable = false)
    var goal: String = goal
        protected set

    @Column(name = "message", nullable = false, unique = true)
    var message: String = message
        protected set

    @Column(name = "mission_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var missionType: MissionType = missionType
        protected set

    @OneToOne(mappedBy = "mission", cascade = [CascadeType.REMOVE])
    var assignMission: AssignMission? = null
        protected set

    fun toResponse(coast: Int?, isFinished: Boolean) = MissionResponse(
        this.id!!,
        this.name,
        this.goal,
        this.message,
        this.missionType,
        coast,
        isFinished
    )
}