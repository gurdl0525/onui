package com.example.onui.domain.mission.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("assigned")
@OnDelete(action = OnDeleteAction.CASCADE)
class AssignMission(
    name: String,
    goal: String,
    coast: Int,
    message: String,
    id: UUID? = null
): Mission(name, goal, message, id) {

    @Column(name = "coast", nullable = false)
    var coast: Int = coast
        protected set
}