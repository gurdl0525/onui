package com.example.onui.domain.user.service

import com.example.onui.domain.user.presentation.dto.response.RiceResponse
import com.example.onui.domain.user.presentation.dto.response.ThemeResponse
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse

interface UserService {

    fun rename(name: String): UserProfileResponse

    fun getProfile(): UserProfileResponse

    fun changeTheme(themeId: String): UserProfileResponse

    fun postTheme(id: String, price: Long)

    fun changeFilter(onFiltering: Boolean): UserProfileResponse

    fun getTheme(): ThemeResponse

    fun changeProfileTheme(profileTheme: String): UserProfileResponse

    fun getRice(): RiceResponse
}