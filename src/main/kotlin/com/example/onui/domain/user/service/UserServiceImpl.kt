package com.example.onui.domain.user.service

import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.exception.ThemeNotFoundException
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import com.example.onui.domain.user.repository.ThemeRepository
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userFacade: UserFacade,
    private val themeRepository: ThemeRepository
) : UserService {

    @Transactional
    override fun rename(name: String): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        return userRepository.save(User(user.sub, name, user.theme, user.id)).toResponse()
    }

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    @Transactional
    override fun changeTheme(themeId: String): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        val theme = themeRepository.findByIdOrNull(themeId) ?: throw ThemeNotFoundException

        return userRepository.save(User(user.sub, user.name, theme, user.id, user.role)).toResponse()
    }
}