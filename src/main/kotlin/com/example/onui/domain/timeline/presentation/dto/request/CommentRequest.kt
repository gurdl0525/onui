package com.example.onui.domain.timeline.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CommentRequest(

    @field:NotBlank(message = "comment는 null일 수 없습니다.")
    @field:Size(max = 50, message = "댓글은 최대 50자 입니다.")
    val comment: String
)
