package com.example.onui.domain.user.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object AlreadyPostedThemeException : BusinessException(ErrorCode.ALREADY_POSTED_THEME)
