package com.countlesswrongs.myshoppinglist.domain.model

data class ShopItem(
    val id: Int,
    val name: String,
    val amount: Int,
    val isOn: Boolean
)
