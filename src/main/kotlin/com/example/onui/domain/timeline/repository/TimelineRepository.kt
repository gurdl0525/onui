package com.example.onui.domain.timeline.repository

import com.example.onui.domain.timeline.entity.Timeline
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.DayOfWeek
import java.util.*

interface TimelineRepository: JpaRepository<Timeline, UUID?> {

    fun findAllByDayOfWeek(dayOfWeek: DayOfWeek, pageable: Pageable): Page<Timeline>
}