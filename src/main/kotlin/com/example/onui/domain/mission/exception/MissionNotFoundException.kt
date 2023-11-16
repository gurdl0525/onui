package com.example.onui.domain.mission.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object MissionNotFoundException : BusinessException(ErrorCode.MISSION_NOT_FOUND)
