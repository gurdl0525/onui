package com.example.onui.domain.auth.repository

import com.example.onui.domain.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.validation.constraints.Email

@Repository
interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {

    fun findByEmail(email: String): RefreshToken?
}