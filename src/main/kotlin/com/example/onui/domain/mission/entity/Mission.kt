package com.example.onui.domain.mission.entity

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "mission")
class Mission (
    goal: String,
    coast: Int,
    id: UUID? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id = id
        protected set

    @Column(name = "goal", nullable = false)
    var goal: String = goal
        protected set

    @Column(name = "coast", nullable = false)
    var coast: Int = coast
        protected set
}