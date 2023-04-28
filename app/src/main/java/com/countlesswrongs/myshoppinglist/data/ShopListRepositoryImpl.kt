package com.countlesswrongs.myshoppinglist.data

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    override fun getShopItemList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        shopList.remove(oldItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with $shopItemId was not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }
}