package com.example.onui.domain.timeline.entity

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "timeline")
class Timeline(
    diary: Diary,
    createdAt: LocalDateTime,
    id: UUID? = null,
    isUpdated: Boolean = false
) {

    @Id
    @Column(name = "id")
    var id: UUID? = id
        protected set

    @OneToOne(fetch = FetchType.LAZY) @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var diary: Diary = diary
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    var dayOfWeek: DayOfWeek = createdAt.dayOfWeek

    @Column(name = "is_updated", nullable = false, columnDefinition = "BIT")
    var isUpdated: Boolean = isUpdated
        protected set

    fun toResponse() = TimelineResponse (
        this.id!!,
        this.diary.title,
        this.diary.content,
        this.diary.mood,
        this.diary.tag,
        this.diary.image,
        this.dayOfWeek,
        this.diary.createdAt,
        this.isUpdated
    )
}