package com.example.onui.domain.user.service

import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse

interface UserService {

    fun rename(name: String): UserProfileResponse

    fun getProfile(): UserProfileResponse

    fun changeTheme(themeId: String): UserProfileResponse

    fun postTheme(id: String)

    fun changeFilter(onFiltering: Boolean): UserProfileResponse
}