package com.example.onui.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 60 * 60 * 24 * 7)
data class RefreshToken (

    @Id
    var token: String,

    @Indexed
    var sub: String
)