package com.example.onui.domain.diary.entity

import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryResponse
import com.example.onui.domain.timeline.entity.Comment
import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import com.example.onui.domain.user.entity.User
import com.example.onui.global.common.entity.BaseTimeEntity
import com.vane.badwordfiltering.BadWordFiltering
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "diary")
class
Diary(
    user: User,
    content: String?,
    mood: Mood,
    tagList: MutableList<String>,
    createdAt: LocalDateTime,
    image: String? = null,
    id: UUID? = null,
    isPosted: Boolean = false
) : BaseTimeEntity(createdAt) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    var id: UUID? = id
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @Column(name = "content", length = 1500)
    var content: String? = content
        protected set

    @Column(name = "mood", nullable = false)
    @Enumerated(EnumType.STRING)
    var mood: Mood = mood
        protected set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "diary_tag", joinColumns = [JoinColumn(name = "diary_id", referencedColumnName = "id")])
    var tagList: MutableList<String> = tagList
        protected set

    @Column(name = "image", nullable = true)
    var image: String? = image
        protected set

    @Column(name = "is_posted", nullable = false, columnDefinition = "BIT")
    var isPosted: Boolean = isPosted
        protected set

    @OneToMany(mappedBy = "timeline", cascade = [CascadeType.REMOVE])
    var commentList: MutableList<Comment> = arrayListOf()

    fun toResponse() = DiaryResponse(
        this.id!!,
        this.mood,
        this.createdAt.toLocalDate()
    )

    fun toDetailResponse() = DiaryDetailResponse(
        this.id!!,
        this.content,
        this.mood,
        this.tagList,
        this.createdAt.toLocalDate(),
        this.image
    )

    fun toTimelineResponse() = TimelineResponse(
        this.id!!,
        this.content,
        this.mood,
        this.tagList,
        this.image,
        this.user.name,
        this.commentList.size,
        this.createdAt.toLocalDate()
    )

    fun toFilteringTimelineResponse(badWordFiltering: BadWordFiltering) = TimelineResponse(
        this.id!!,
        badWordFiltering.change(this.content),
        this.mood,
        this.tagList,
        this.image,
        this.user.name,
        this.commentList.size,
        this.createdAt.toLocalDate()
    )
}