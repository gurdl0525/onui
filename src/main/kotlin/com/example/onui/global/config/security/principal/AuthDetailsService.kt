package com.example.onui.global.config.security.principal

import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails =
        AuthDetails(userRepository.findByEmail(email) ?: throw InvalidTokenException)
}