package com.example.onui.domain.shop.service

import com.example.onui.domain.shop.presentation.dto.response.ShopAllListResponse
import com.example.onui.domain.shop.presentation.dto.response.ShopListResponse

interface ShopService {

    fun buy(id: String): ShopListResponse

    fun getShopList(): ShopListResponse

    fun getAllShopList(): ShopAllListResponse
}