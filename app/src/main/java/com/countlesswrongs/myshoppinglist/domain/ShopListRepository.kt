package com.countlesswrongs.myshoppinglist.domain

import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

interface ShopListRepository {

    fun getShopItemList(): List<ShopItem>

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun addShopItem(shopItem: ShopItem)

}