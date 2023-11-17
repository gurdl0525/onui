package com.example.onui.domain.shop.service

import com.example.onui.domain.shop.entity.BoughtTheme
import com.example.onui.domain.shop.exception.AlreadyBoughtThemeException
import com.example.onui.domain.shop.exception.CanNotBuyThemeException
import com.example.onui.domain.shop.presentation.dto.response.ShopAllListResponse
import com.example.onui.domain.shop.presentation.dto.response.ShopAllResponse
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

        if (boughtThemeRepository.existsById(
                BoughtTheme.IdClass(
                    theme.id, user.id
                )
            ) || theme.price == 0L
        ) throw AlreadyBoughtThemeException

        val rice = user.rice - theme.price

        if (rice < 0) throw CanNotBuyThemeException

        boughtThemeRepository.save(BoughtTheme(user, theme))
        userRepository.save(
            User(user.sub, user.name, user.profileTheme, user.theme, rice, user.id, user.role, user.onFiltering)
        )

        return getShopList(user)
    }

    override fun getShopList(): ShopListResponse = getShopList(userFacade.getCurrentUser())
    override fun getAllShopList(): ShopAllListResponse {
        val user = userFacade.getCurrentUser()

        return ShopAllListResponse(themeRepository.findAll().map {
            ShopAllResponse(
                it.id, it.price, it.price == 0L || boughtThemeRepository.existsById(BoughtTheme.IdClass(it.id, user.id))
            )
        }.toMutableList())
    }

    private fun getShopList(user: User) = ShopListResponse(themeRepository.findAll().filter {
        it.price != 0L && !boughtThemeRepository.existsById(BoughtTheme.IdClass(it.id, user.id))
    }.map {
        it.toShopResponse()
    }.toMutableList())
}