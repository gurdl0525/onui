package com.example.onui.domain.shop.service

import com.example.onui.domain.shop.entity.BoughtTheme
import com.example.onui.domain.shop.exception.AlreadyBoughtThemeException
import com.example.onui.domain.shop.exception.CanNotBuyThemeException
import com.example.onui.domain.shop.presentation.dto.response.ShopListResponse
import com.example.onui.domain.shop.repository.BoughtThemeRepository
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.exception.ThemeNotFoundException
import com.example.onui.domain.user.repository.ThemeRepository
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShopServiceImpl(
    private val boughtThemeRepository: BoughtThemeRepository,
    private val themeRepository: ThemeRepository,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
) : ShopService {

    @Transactional
    override fun buy(id: String): ShopListResponse {

        val user = userFacade.getCurrentUser()

        val theme = themeRepository.findByIdOrNull(id) ?: throw ThemeNotFoundException

        if (boughtThemeRepository.existsById(BoughtTheme.IdClass(theme.id, user.id)) || theme.price == 0L)
            throw AlreadyBoughtThemeException

        val rice = user.rice - theme.price

        if (rice < 0) throw CanNotBuyThemeException

        boughtThemeRepository.save(BoughtTheme(user, theme))
        userRepository.save(
            User(user.sub, user.name, user.profileTheme, user.theme, rice, user.id, user.role, user.onFiltering)
        )

        return getShopList(user)
    }

    private fun getShopList(user: User) = ShopListResponse(themeRepository.findAll().map {
        it.toShopResponse(boughtThemeRepository.existsById(BoughtTheme.IdClass(it.id, user.id)))
    }.toMutableList())
}