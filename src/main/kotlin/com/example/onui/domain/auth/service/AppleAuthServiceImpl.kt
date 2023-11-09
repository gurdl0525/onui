package com.example.onui.domain.auth.service

import com.example.onui.domain.auth.presentation.dto.response.TokenResponse
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.config.error.exception.InvalidTokenException
import com.example.onui.global.config.jwt.AppleJwtParser
import com.example.onui.global.config.jwt.TokenProvider
import com.example.onui.infra.feign.apple.AppleClient
import com.example.onui.infra.feign.apple.dto.ApplePublicKey
import com.example.onui.infra.feign.apple.dto.ApplePublicKeys
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.util.*


@Service
class AppleAuthServiceImpl(
    private val appleClient: AppleClient,
    private val jwtProvider: TokenProvider,
    private val jwtParser: AppleJwtParser,
    private val userRepository: UserRepository
) : AppleAuthService {

    private companion object {
        const val ALG_HEADER_KEY = "alg"
        const val KID_HEADER_KEY = "kid"
        const val NAME = "user-"
    }

    @Transactional
    override fun signUp(idToken: String): TokenResponse {

        val token = parseIdToken(idToken)

        val sub = token.subject


        val user = userRepository.findBySub(sub)
            ?: userRepository.save(
                User(
                    sub,
                    token.get("email", String::class.java)
                        ?: (NAME + UUID.randomUUID().toString().replace("-", ""))
                )
            )

        return jwtProvider.receiveToken(user.sub)
    }

    private fun parseIdToken(idToken: String) = jwtProvider.parseClaims(
        idToken, generatePublicKey(jwtParser.parseHeaders(idToken), appleClient.applePublicKeys())
    )

    private fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeys
    ): PublicKey {

        val publicKey: ApplePublicKey =
            applePublicKeys.matchesKey(tokenHeaders[ALG_HEADER_KEY]!!, tokenHeaders[KID_HEADER_KEY]!!)
                ?: throw InvalidTokenException

        return try {
            KeyFactory.getInstance(publicKey.kty).generatePublic(publicKey.publicKeySpec())
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        } catch (e: InvalidKeySpecException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        }
    }
}