package com.example.onui.domain.timeline.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.timeline.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, UUID?> {

    fun findAllByTimelineOrderByCreatedAtAsc(timeline: Diary): MutableList<Comment>
}