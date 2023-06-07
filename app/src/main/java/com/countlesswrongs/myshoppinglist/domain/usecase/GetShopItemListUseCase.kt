package com.countlesswrongs.myshoppinglist.domain.usecase

import androidx.lifecycle.LiveData
import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import javax.inject.Inject

class GetShopItemListUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItemList()
    }
}
