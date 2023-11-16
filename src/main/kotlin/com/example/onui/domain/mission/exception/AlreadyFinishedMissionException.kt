package com.example.onui.domain.mission.exception

import com.example.onui.global.config.error.data.ErrorCode
import com.example.onui.global.config.error.exception.BusinessException

object AlreadyFinishedMissionException : BusinessException(ErrorCode.ALREADY_FINISHED_MISSION)