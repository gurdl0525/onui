package com.example.onui.domain.mission.entity

import java.util.UUID
import javax.persistence.*

@Entity(name = "mission")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "mission_type")
abstract class Mission (
    name: String,
    goal: String,
    message: String,
    id: UUID? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id = id
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "goal", nullable = false)
    var goal: String = goal
        protected set

    @Column(name = "message", nullable = false)
    var message: String = message
        protected set
}