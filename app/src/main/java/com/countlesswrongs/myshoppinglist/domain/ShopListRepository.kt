package com.countlesswrongs.myshoppinglist.domain

import androidx.lifecycle.LiveData
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

interface ShopListRepository {

    fun getShopItemList(): LiveData<List<ShopItem>>

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    suspend fun addShopItem(shopItem: ShopItem)

}