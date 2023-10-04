package com.example.onui.domain.user.service

import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userFacade: UserFacade
): UserService {

    @Transactional
    override fun rename(name: String): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        return userRepository.save(User(
            user.email,
            name,
            user.type,
            user.id
        )).toResponse()
    }
}