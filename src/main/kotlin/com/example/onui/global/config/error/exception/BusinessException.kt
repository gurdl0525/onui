package com.example.onui.global.config.error.exception

import com.example.onui.global.config.error.data.ErrorCode


open class BusinessException(val errorCode: ErrorCode): RuntimeException(errorCode.message)