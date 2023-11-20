package com.example.onui.infra.fcm

interface NotificationService {

    fun sendByTokenList(tokenList: MutableSet<String>, title: String, body: String)
}
