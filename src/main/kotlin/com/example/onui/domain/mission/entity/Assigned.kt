package com.example.onui.domain.mission.entity

import com.example.onui.domain.user.entity.User
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity(name = "assigned_table")
@IdClass(Assigned.IdClass::class)
class Assigned(
    user: User,
    mission: Mission,
    isFinished: Boolean = false
) : Persistable<Assigned.IdClass> {

    @Id
    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false, columnDefinition = "BINARY(16)")
    var mission: Mission = mission
        protected set

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    var user: User = user
        protected set

    @Column(name = "is_finished", nullable = false, columnDefinition = "BIT")
    var isFinished: Boolean = isFinished
        protected set

    data class IdClass(
        var mission: UUID? = null,
        var user: UUID? = null
    ) : Serializable

    override fun getId() = IdClass(this.mission.id, this.user.id)

    override fun isNew() = mission.id == null || user.id == null
}