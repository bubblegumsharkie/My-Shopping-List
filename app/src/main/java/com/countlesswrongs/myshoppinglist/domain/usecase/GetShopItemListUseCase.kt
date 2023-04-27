package com.countlesswrongs.myshoppinglist.domain.usecase

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): List<ShopItem> {
        return shopListRepository.getShopItemList()
    }
}
