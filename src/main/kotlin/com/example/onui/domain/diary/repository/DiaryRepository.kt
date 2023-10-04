package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DiaryRepository: JpaRepository<Diary, UUID?> {

    fun findAllByUserAndYearAndMonth(user: User, year: Int, month: Int): MutableList<Diary>?

    fun existsByUserAndYearAndMonth(user: User, year: Int, month: Int): Boolean
}