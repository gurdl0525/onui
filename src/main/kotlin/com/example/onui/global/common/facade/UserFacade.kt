package com.info.chattingserver.global.common.facade

import com.info.chattingserver.domain.user.entity.User
import com.info.chattingserver.domain.user.repository.UserRepository
import com.info.chattingserver.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {

    fun getCurrentUser(): User = userRepository.findByAccountId(SecurityContextHolder.getContext().authentication.name)
        ?: throw InvalidTokenException
}