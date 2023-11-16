package com.example.onui.domain.shop.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object AlreadyBoughtThemeException : BusinessException(ErrorCode.ALREADY_BOUGHT_THEME)
