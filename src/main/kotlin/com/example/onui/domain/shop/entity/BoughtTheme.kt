package com.example.onui.domain.shop.entity

import com.example.onui.domain.user.entity.Theme
import com.example.onui.domain.user.entity.User
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity(name = "bought_theme")
@IdClass(BoughtTheme.IdClass::class)
class BoughtTheme(
    user: User,
    theme: Theme
) : Persistable<BoughtTheme.IdClass> {

    @Id
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    var theme: Theme = theme
        protected set

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    var user: User = user
        protected set

    data class IdClass(
        var theme: String? = null,
        var user: UUID? = null
    ) : Serializable

    override fun getId() = IdClass(this.theme.id, this.user.id)

    override fun isNew() = user.id == null
}
