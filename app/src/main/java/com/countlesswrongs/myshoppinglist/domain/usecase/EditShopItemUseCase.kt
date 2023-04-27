package com.countlesswrongs.myshoppinglist.domain.usecase

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}
