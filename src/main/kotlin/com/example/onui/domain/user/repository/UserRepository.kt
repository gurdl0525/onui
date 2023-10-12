package com.example.onui.domain.user.repository

import com.example.onui.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID?> {

    fun findBySub(sub: String): User?
}