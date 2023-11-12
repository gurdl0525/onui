package com.example.onui.domain.timeline.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object TimelineNotFoundException : BusinessException(ErrorCode.TIMELINE_NOT_FOUND)
