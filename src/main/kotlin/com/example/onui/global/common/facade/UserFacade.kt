package com.example.onui.global.common.facade

import com.example.onui.domain.user.entity.Role
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.error.exception.InvalidTokenException
import com.example.onui.global.config.error.exception.PermissionDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {

    fun getCurrentUser(): User = userRepository.findBySub(SecurityContextHolder.getContext().authentication.name)
        ?: throw InvalidTokenException

    fun getAdmin(): User {
        val user = userRepository.findBySub(SecurityContextHolder.getContext().authentication.name)
            ?: throw InvalidTokenException

        if (user.role != Role.ADMIN) throw PermissionDeniedException

        return user
    }
}