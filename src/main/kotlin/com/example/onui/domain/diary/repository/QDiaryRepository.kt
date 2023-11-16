package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.presentation.response.DiaryResponse
import com.example.onui.domain.user.entity.User

interface QDiaryRepository {

    fun findThreeDayAgoByUser(user: User): MutableList<Diary>

    fun findSevenDayAgoByUser(user: User): MutableList<DiaryResponse>
}