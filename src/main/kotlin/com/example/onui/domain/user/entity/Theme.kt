package com.example.onui.domain.user.entity

import com.example.onui.domain.shop.entity.BoughtTheme
import com.example.onui.domain.shop.presentation.dto.response.ShopResponse
import javax.persistence.*

@Entity(name = "theme")
class Theme(
    id: String,
    price: Long = 0
) {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(18)", nullable = false)
    var id: String = id
        protected set

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    var user: MutableList<User> = arrayListOf()
        protected set

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    var boughtTheme: MutableList<BoughtTheme> = arrayListOf()
        protected set

    @Column(name = "price", nullable = false)
    var price: Long = price
        protected set

    fun toShopResponse() = ShopResponse(
        this.id,
        this.price
    )
}