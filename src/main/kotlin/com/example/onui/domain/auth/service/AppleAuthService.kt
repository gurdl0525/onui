package com.example.onui.domain.auth.service

interface AppleAuthService {

    fun test(): Any

    fun signUp(code: String): Any
}