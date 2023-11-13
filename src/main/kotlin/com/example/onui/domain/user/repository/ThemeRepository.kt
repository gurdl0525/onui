package com.example.onui.domain.user.repository

import com.example.onui.domain.user.entity.Theme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ThemeRepository : JpaRepository<Theme, String> {
}