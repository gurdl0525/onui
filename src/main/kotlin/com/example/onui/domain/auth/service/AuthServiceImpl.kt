package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.repository.RefreshTokenRepository
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.jwt.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
    private val tokenProvider: TokenProvider,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthService {

    override fun reissue(refreshToken: String) = tokenProvider.reissue(refreshToken)

    @Transactional
    override fun signOut() {

        val user = userFacade.getCurrentUser()

        refreshTokenRepository.deleteBySub(user.sub)
        userRepository.delete(user)
    }
}