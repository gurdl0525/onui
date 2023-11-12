package com.example.onui.domain.timeline.entity

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.timeline.presentation.dto.response.CommentResponse
import com.example.onui.domain.user.entity.User
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "comment")
class Comment(
    content: String,
    user: User,
    timeline: Diary,
    id: UUID? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeline_id", nullable = false)
    var timeline: Diary = timeline
        protected set

    @Column(name = "content", columnDefinition = "VARCHAR(50)", nullable = false)
    var content: String = content
        protected set

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    fun toResponse() = CommentResponse(
        this.id!!,
        this.timeline.id!!,
        this.user.id!!,
        this.content
    )
}