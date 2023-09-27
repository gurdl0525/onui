package com.example.onui.infra.feign.apple

import org.springframework.util.Base64Utils
import java.math.BigInteger
import java.security.spec.RSAPublicKeySpec

data class ApplePublicKey(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
) {

    private fun getN() = BigInteger(1, Base64Utils.decodeFromUrlSafeString(this.n))

    private fun getE() = BigInteger(1, Base64Utils.decodeFromUrlSafeString(this.e))

    fun getPublicKeySpec() = RSAPublicKeySpec(getN(), getE())
}
