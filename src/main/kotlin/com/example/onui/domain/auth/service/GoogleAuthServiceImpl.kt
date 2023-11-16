package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.auth.repository.RefreshTokenRepository
import com.example.onui.domain.mission.entity.MissionType
import com.example.onui.domain.mission.repository.MissionRepository
import com.example.onui.domain.mission.service.MissionService
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.ThemeRepository
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.error.exception.InvalidTokenException
import com.example.onui.global.config.jwt.TokenProvider
import com.example.onui.infra.feign.google.GoogleAuthClient
import com.example.onui.infra.feign.google.GoogleInfoClient
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GoogleAuthServiceImpl(
    private val googleAuth: GoogleAuthClient,
    private val googleInfo: GoogleInfoClient,
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val themeRepository: ThemeRepository,
    private val missionService: MissionService,
    private val missionRepository: MissionRepository
) : GoogleAuthService {

    private companion object {
        const val ALT = "json"
        val logger = KotlinLogging.logger {}
        const val DEFAULT = "FFFFFF"
        const val DEFAULT_ID = "default"
    }

    @Transactional
    override fun oauthGoogleSignIn(token: String): TokenResponse {

        logger.info { token }

        val response = try {
            googleInfo.googleInfo(ALT, token)
        } catch (e: Exception) {
            throw InvalidTokenException
        }

        refreshTokenRepository.findBySub(response.sub)?.let {
            refreshTokenRepository.delete(it)
        }

        googleAuth.revokeToken(token)
        val tokenResponse = tokenProvider.receiveToken(response.sub)

        userRepository.findBySub(response.sub) ?: userRepository.save(
            User(
                response.sub,
                response.name,
                DEFAULT,
                themeRepository.findByIdOrNull(DEFAULT_ID)!!
            )
        ).apply {
            missionService.assignMission(this, missionRepository.findAllByMissionType(MissionType.RANDOM))
        }

        return tokenResponse
    }
}