package com.example.onui.domain.auth.service

import com.example.onui.global.config.error.exception.InvalidTokenException
import com.example.onui.global.config.jwt.AppleJwtParser
import com.example.onui.global.config.jwt.TokenProvider
import com.example.onui.global.env.AppleProperty
import com.example.onui.infra.feign.apple.AppleClient
import com.example.onui.infra.feign.apple.dto.ApplePublicKey
import com.example.onui.infra.feign.apple.dto.ApplePublicKeys
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KLogger
import mu.KotlinLogging
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.springframework.stereotype.Service
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@Service
class AppleAuthServiceImpl(
    private val appleClient: AppleClient,
    private val jwtProvider: TokenProvider,
    private val jwtParser: AppleJwtParser,
    private val appleProperty: AppleProperty
) : AppleAuthService {

    private companion object {
        val logger: KLogger = KotlinLogging.logger { }
        const val ALG_HEADER_KEY = "alg"
        const val KID_HEADER_KEY = "kid"
        const val ALG = "ES256"
        const val AUD = "https://appleid.apple.com"
    }

    override fun test(): Any {
        return "$AUD/auth/authorize" +
                "?client_id=${appleProperty.clientId}" +
                "&redirect_uri=http://127.0.0.1/auth/oauth/apple/token" +
                "&response_type=code" +
                "&scope=name%20email" +
                "&response_mode=form_post"
    }

    override fun signUp(code: String): Any {

        logger.error { parseIdToken(code) }

        return parseIdToken(
            appleClient.getToken(
                code,
                appleProperty.clientId,
                makeClientSecret()
            ).idToken
        )
    }

    fun parseIdToken(idToken: String): Any = jwtProvider.parseClaims(
        idToken, generatePublicKey(jwtParser.parseHeaders(idToken), appleClient.applePublicKeys())
    )

    fun generatePublicKey(tokenHeaders: MutableMap<String?, String?>, applePublicKeys: ApplePublicKeys): PublicKey {

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

    fun makeClientSecret(): String = Jwts.builder()
        .setHeaderParam(KID_HEADER_KEY, appleProperty.keyId)
        .setHeaderParam(ALG_HEADER_KEY, ALG)
        .setIssuer(appleProperty.issuer)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant()))
        .setAudience(AUD)
        .setSubject(appleProperty.clientId)
        .signWith(SignatureAlgorithm.ES256, getPrivateKey())
        .compact()

    private fun getPrivateKey(): PrivateKey {
        Security.addProvider(BouncyCastleProvider())
        val converter = JcaPEMKeyConverter().setProvider("BC")
        return try {
            val privateKeyBytes: ByteArray = Base64.getDecoder().decode(appleProperty.authKey)
            val privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes)
            converter.getPrivateKey(privateKeyInfo)
        } catch (e: Exception) {
            throw RuntimeException("Error converting private key from String", e)
        }
    }
}