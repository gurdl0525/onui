package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DiaryRepository : JpaRepository<Diary, UUID?> {

    fun findAllByUserAndYearAndMonthOrderByCreatedAtAsc(user: User, year: Int, month: Int): MutableList<Diary>?

    fun findByUserAndYearAndMonthAndDay(user: User, year: Int, month: Int, day: Int): Diary?

    fun existsByIdAndIsPosted(id: UUID, isPosted: Boolean): Boolean
}