package com.example.onui.infra.oauth.apple

import com.example.onui.infra.feign.apple.AppleClient
import com.example.onui.infra.feign.apple.ApplePublicKey
import com.example.onui.infra.feign.apple.env.AppleProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AppleUtils(
    private val appleClient: AppleClient,
    private val appleProperty: AppleProperty
) {

    fun test(): MutableList<ApplePublicKey> {
        return appleClient.applePublicKeys().keys
    }

    companion object{
        private const val SIGN_ALGORITHM_HEADER_KEY = "alg"
        private const val KEY_ID_HEADER_KEY = "kid"
    }

//    fun generatePublicKey(headers: Map<String, String>, applePublicKeys: ApplePublicKeys): PublicKey {
//
//        val applePublicKey = applePublicKeys.matchesKey(
//            headers[SIGN_ALGORITHM_HEADER_KEY]!!,
//            headers[KEY_ID_HEADER_KEY]!!
//        ) ?: throw IllegalStateException("")
//
//        return generatePublicKeyWithApplePublicKey(applePublicKey)
//    }
//
//    private fun generatePublicKeyWithApplePublicKey(publicKey: ApplePublicKey) = try {
//
//        KeyFactory.getInstance(publicKey.kty).generatePublic(publicKey.getPublicKeySpec())
//
//    } catch (exception: NoSuchAlgorithmException) {
//        throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
//
//    } catch (exception: InvalidKeySpecException) {
//        throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
//
//    }
//
//    fun parsePublicKeyAndGetClaims(idToken: String, publicKey: PublicKey): Claims {
//        return try {
//            Jwts.parser()
//                .setSigningKey(publicKey)
//                .parseClaimsJws(idToken)
//                .body
//        } catch (e: ExpiredJwtException) {
//            throw ExpiredTokenException
//        } catch (e: UnsupportedJwtException) {
//            throw InvalidTokenException
//        } catch (e: MalformedJwtException) {
//            throw InvalidTokenException
//        } catch (e: SignatureException) {
//            throw InvalidTokenException
//        } catch (e: IllegalArgumentException) {
//            throw InvalidTokenException
//        }
//    }
//
//    fun validate(claims: Claims): Boolean = claims.issuer.contains(appleProperty.iss) &&
//            claims.audience.equals(appleProperty.clientId) && claims.get("nonce", String::class.java) == appleProperty.nonce
}