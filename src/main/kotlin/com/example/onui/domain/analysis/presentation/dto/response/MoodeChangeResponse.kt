package com.example.onui.domain.analysis.presentation.dto.response

data class MoodeChangeResponse (
    var worst: Int,
    var bad: Int,
    var notBad: Int,
    var fine: Int,
    var good: Int
) {

    companion object {
        fun setUp() = MoodeChangeResponse(0, 0, 0,0, 0)
    }
}