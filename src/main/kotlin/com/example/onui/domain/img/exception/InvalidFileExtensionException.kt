package com.example.onui.domain.img.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object InvalidFileExtensionException : BusinessException(ErrorCode.INVALID_FILE_EXTENSION)
