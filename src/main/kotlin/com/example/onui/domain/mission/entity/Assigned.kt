package com.example.onui.domain.mission.entity

import com.example.onui.domain.user.entity.User
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.UUID
import javax.persistence.*

@Entity(name = "assigned_table")
@IdClass(Assigned.IdClass::class)
class Assigned(
    user: User,
    mission: Mission
): Persistable<Assigned.IdClass> {

    @Id @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    var mission: Mission = mission
        protected set

    @Id @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    data class IdClass(
        var mission: UUID? =null,
        var user: UUID? = null
    ): Serializable

    override fun getId() = IdClass(this.mission.id, this.user.id)

    override fun isNew() = mission.id == null || user.id == null
}