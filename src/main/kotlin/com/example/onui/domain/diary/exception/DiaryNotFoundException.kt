package com.example.onui.domain.diary.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object DiaryNotFoundException : BusinessException(ErrorCode.DIARY_NOT_FOUND)
