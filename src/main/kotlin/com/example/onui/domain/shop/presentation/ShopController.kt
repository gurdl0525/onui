package com.example.onui.domain.shop.presentation

import com.example.onui.domain.shop.presentation.dto.response.ShopListResponse
import com.example.onui.domain.shop.service.ShopService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/shop")
class ShopController(
    private val shopService: ShopService
) {

    @PostMapping("/buy")
    fun buyTheme(
        @RequestParam("id", required = true)
        id: String
    ): ShopListResponse = shopService.buy(id)

    @GetMapping
    fun getShop(): ShopListResponse = shopService.getShopList()
}