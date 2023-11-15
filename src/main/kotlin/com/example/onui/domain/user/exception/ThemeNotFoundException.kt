package com.example.onui.domain.user.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object ThemeNotFoundException : BusinessException(ErrorCode.THEME_NOT_FOUND)