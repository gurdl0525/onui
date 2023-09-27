package com.example.onui.infra.feign.apple

data class ApplePublicKeys(
    val keys: MutableList<ApplePublicKey>
) {

    fun matchesKey(alg: String, kid: String): ApplePublicKey? {

        return try {
            keys.first {
                it.alg == alg && it.kid == kid
            }
        }catch (e: NoSuchElementException) {
            null
        }
    }
}
