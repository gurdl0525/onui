package com.example.onui.global.common.facade

import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {

    fun getCurrentUser(): User = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)
        ?: throw InvalidTokenException
}