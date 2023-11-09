package com.example.onui.domain.mission.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("general")
@OnDelete(action = OnDeleteAction.CASCADE)
class GeneralMission(
    name: String,
    goal: String,
    message: String,
    id: UUID? = null
): Mission(name, goal, message, id) {
}