package com.example.onui.domain.diary.entity

import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryResponse
import com.example.onui.domain.user.entity.User
import com.example.onui.global.common.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "diary")
class
Diary(
    user: User,
    title: String,
    content: String,
    mood: Int,
    tag: MutableList<String>,
    createdAt: LocalDateTime,
    image: String? = null,
    id: UUID? = null
): BaseTimeEntity(createdAt) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    var id: UUID? = id
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @Column(name = "title", length = 30, nullable = false)
    var title: String= title
        protected set

    @Column(name = "content", length = 1500, nullable = false)
    var content: String = content
        protected set

    @Column(name = "mood", nullable = false)
    var mood: Int = mood
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "diary_tag", joinColumns = [JoinColumn(name= "diary_id", referencedColumnName = "id")])
    var tag: MutableList<String> = tag
        protected set

    @Column(name = "image", nullable = true)
    var image: String? = image
        protected set

    fun toResponse() = DiaryResponse(
        this.id!!,
        this.mood,
        this.createdAt.toLocalDate()
    )

    fun toDetailResponse() = DiaryDetailResponse(
        this.id!!,
        this.title,
        this.content,
        this.mood,
        this.tag,
        this.createdAt.toLocalDate()
    )
}