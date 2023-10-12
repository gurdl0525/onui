package com.example.onui.domain.user.entity

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    sub: String,
    name: String,
    id: UUID? = null
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "sub", nullable = false, length = 30, unique = true)
    var sub: String = sub
        protected set

    @Column(name = "name", nullable = false, length = 255)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var diaryList: MutableList<Diary> = arrayListOf()

    fun toResponse() = UserProfileResponse(
        this.sub,
        this.name
    )
}