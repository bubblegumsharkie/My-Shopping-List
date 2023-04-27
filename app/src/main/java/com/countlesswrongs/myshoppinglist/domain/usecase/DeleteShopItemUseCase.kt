package com.countlesswrongs.myshoppinglist.domain.usecase

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(shopItemId: Int) {
        shopListRepository.deleteShopItem(shopItemId)
    }
}
