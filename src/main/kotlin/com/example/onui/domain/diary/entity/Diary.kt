package com.example.onui.domain.diary.entity

import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.example.onui.domain.diary.presentation.response.DiaryResponse
import com.example.onui.domain.user.entity.User
import com.example.onui.global.common.entity.BaseTimeEntity
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity(name = "diary")
class
Diary(
    user: User,
    text: String?,
    mood: Int,
    createdAt: LocalDate,
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

    @Column(name = "text")
    var text: String? = text
        protected set

    @Min(1, message = "mood는 1부터 입니다.")
    @Max(5, message = "mood는 5부터 입니다.")
    @Column(name = "mood", nullable = false)
    var mood: Int = mood
        protected set

    fun toResponse() = DiaryResponse(
        this.id!!,
        this.mood,
        LocalDate.of(this.year, this.month, this.day)
    )

    fun toDetailResponse() = DiaryDetailResponse(
        this.id!!,
        this.text,
        this.mood,
        LocalDate.of(this.year, this.month, this.day)
    )
}