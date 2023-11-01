package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.user.entity.User

interface QDiaryRepository {
    fun find3DayAgoByUser(user: User): Diary?
}