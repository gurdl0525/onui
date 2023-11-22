package com.example.onui.domain.diary.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object InvalidImageUrlException : BusinessException(ErrorCode.INVALID_IMG_URL) {
    private fun readResolve(): Any = InvalidImageUrlException
}
