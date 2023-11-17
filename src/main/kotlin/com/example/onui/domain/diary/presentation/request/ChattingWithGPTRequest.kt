package com.example.onui.domain.diary.presentation.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ChattingWithGPTRequest(

    @field:NotNull(message = "null일 수 없습니다.")
    @field:NotEmpty(message = "비어있을 수 없습니다.")
    val tagList: MutableList<String>?,

    @field:Size(max = 1000, message = "1000자를 넘을 수 없습니다.")
    @field:NotBlank(message = "null일 수 없습니다.")
    val text: String?
)
