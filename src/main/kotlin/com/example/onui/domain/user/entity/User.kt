package com.example.onui.domain.user.entity

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.mission.entity.Assigned
import com.example.onui.domain.timeline.entity.Comment
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    sub: String,
    name: String,
    profileTheme: String,
    theme: Theme,
    rice: Long = 0,
    id: UUID? = null,
    role: Role = Role.USER,
    onFiltering: Boolean = false
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "sub", nullable = false, length = 60, unique = true)
    var sub: String = sub
        protected set

    @Column(name = "name", nullable = false, length = 255)
    var name: String = name
        protected set

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = role
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var diaryList: MutableList<Diary> = arrayListOf()
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var commentList: MutableList<Comment> = arrayListOf()
        protected set

    @Column(name = "profile_theme", nullable = false, columnDefinition = "CHAR(6)")
    var profileTheme: String = profileTheme
        protected set

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    var theme: Theme = theme
        protected set

    @Column(name = "on_filtering", nullable = false, columnDefinition = "BIT")
    var onFiltering: Boolean = onFiltering
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var assignedList: MutableList<Assigned> = arrayListOf()
        protected set

    @Column(name = "rice", nullable = false)
    var rice: Long = rice
        protected set

    fun toResponse() = UserProfileResponse(
        this.sub,
        this.name,
        this.profileTheme,
        this.theme.id,
        this.onFiltering
    )
}