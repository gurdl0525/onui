package com.example.onui.domain.diary.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object AlreadyWroteDiaryException : BusinessException(ErrorCode.ALREADY_WROTE_DIARY)
