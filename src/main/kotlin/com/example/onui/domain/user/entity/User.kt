package com.example.onui.domain.user.entity

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.user.entity.type.UserType
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    email: String,
    name: String,
    type: UserType,
    id: UUID? = null
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var diaryList: MutableList<Diary> = arrayListOf()

    @Enumerated(EnumType.STRING)
    var type: UserType = type
        protected set

    fun toResponse() = UserProfileResponse(
        this.email,
        this.name
    )
}