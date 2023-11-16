package com.example.onui.domain.user.service

import com.example.onui.domain.shop.entity.BoughtTheme
import com.example.onui.domain.shop.exception.NotBoughtThemeException
import com.example.onui.domain.shop.repository.BoughtThemeRepository
import com.example.onui.domain.user.entity.Theme
import com.example.onui.domain.user.entity.User
import com.example.onui.domain.user.exception.AlreadyPostedThemeException
import com.example.onui.domain.user.exception.ThemeNotFoundException
import com.example.onui.domain.user.presentation.dto.response.RiceResponse
import com.example.onui.domain.user.presentation.dto.response.ThemeResponse
import com.example.onui.domain.user.presentation.dto.response.UserProfileResponse
import com.example.onui.domain.user.repository.ThemeRepository
import com.example.onui.domain.user.repository.UserRepository
import com.example.onui.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userFacade: UserFacade,
    private val themeRepository: ThemeRepository,
    private val boughtThemeRepository: BoughtThemeRepository
) : UserService {

    @Transactional
    override fun rename(name: String): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        return userRepository.save(
            User(
                user.sub,
                name,
                user.profileTheme,
                user.theme,
                user.rice,
                user.id,
                user.role,
                user.onFiltering
            )
        ).toResponse()
    }

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    @Transactional
    override fun changeTheme(themeId: String): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        val theme = themeRepository.findByIdOrNull(themeId) ?: throw ThemeNotFoundException

        if (theme.price != 0L && boughtThemeRepository.findByIdOrNull(BoughtTheme.IdClass(theme.id, user.id)) == null)
            throw NotBoughtThemeException

        return userRepository.save(
            User(
                user.sub,
                user.name,
                user.profileTheme,
                theme,
                user.rice,
                user.id,
                user.role,
                user.onFiltering
            )
        ).toResponse()
    }

    @Transactional
    override fun postTheme(id: String, price: Long) {

        userFacade.getAdmin()

        if (themeRepository.existsById(id)) throw AlreadyPostedThemeException

        themeRepository.save(Theme(id, price))
    }

    @Transactional
    override fun changeFilter(onFiltering: Boolean): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        return userRepository.save(
            User(
                user.sub,
                user.name,
                user.profileTheme,
                user.theme,
                user.rice,
                user.id,
                user.role,
                onFiltering
            )
        ).toResponse()
    }

    override fun getTheme() = ThemeResponse(userFacade.getCurrentUser().theme.id)

    @Transactional
    override fun changeProfileTheme(profileTheme: String): UserProfileResponse {
        val user = userFacade.getCurrentUser()

        return userRepository.save(
            User(
                user.sub,
                user.name,
                profileTheme,
                user.theme,
                user.rice,
                user.id,
                user.role,
                user.onFiltering
            )
        ).toResponse()
    }

    override fun getRice() = RiceResponse(userFacade.getCurrentUser().rice)
}