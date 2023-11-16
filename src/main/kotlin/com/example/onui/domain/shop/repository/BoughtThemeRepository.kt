package com.example.onui.domain.shop.repository

import com.example.onui.domain.shop.entity.BoughtTheme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoughtThemeRepository : JpaRepository<BoughtTheme, BoughtTheme.IdClass> {

}
