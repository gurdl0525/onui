package com.example.onui.domain.diary.entity

enum class Mood(
    coast: Int
) {
    WORST(-10),
    BAD(-5),
    NOT_BAD(0),
    FINE(5),
    GOOD(10)
}