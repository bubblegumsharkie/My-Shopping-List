package com.countlesswrongs.myshoppinglist.domain.model


data class ShopItem(
    val name: String,
    val amount: Int,
    val isOn: Boolean,
    var id: Int = UNDEFINED_ID
) {

    companion object {

        const val UNDEFINED_ID = -1
    }
}
