package com.example.onui.domain.auth.service

import com.example.onui.global.common.facade.UserFacade
import com.example.onui.global.config.jwt.TokenProvider
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val tokenProvider: TokenProvider,
    private val userFacade: UserFacade
): AuthService {

    override fun reissue(refreshToken: String) = tokenProvider.reissue(refreshToken)
}