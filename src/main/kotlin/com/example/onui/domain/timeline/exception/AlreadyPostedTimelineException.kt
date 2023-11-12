package com.example.onui.domain.timeline.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object AlreadyPostedTimelineException : BusinessException(ErrorCode.ALREADY_POSTED_TIMELINE)
