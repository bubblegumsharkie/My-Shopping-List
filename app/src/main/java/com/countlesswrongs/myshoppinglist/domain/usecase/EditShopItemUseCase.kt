package com.countlesswrongs.myshoppinglist.domain.usecase

import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}
