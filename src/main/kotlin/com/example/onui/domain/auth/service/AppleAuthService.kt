package com.example.onui.domain.auth.service

interface AppleAuthService {

    fun signUp(idToken: String): Any
}