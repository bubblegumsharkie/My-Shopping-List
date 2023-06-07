package com.countlesswrongs.myshoppinglist.domain.usecase

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}
