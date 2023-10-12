package com.example.onui.global.common.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity(
    val createdAt: LocalDateTime
) {

    @Column(name = "year", nullable = false)
    var year: Int = createdAt.year
        protected set

    @Min(1) @Max(12)
    @Column(name = "month", nullable = false)
    var month: Int = createdAt.monthValue
        protected set

    @Min(1) @Max(31)
    @Column(name = "day", nullable = false)
    var day: Int = createdAt.dayOfMonth
        protected set
}