package com.countlesswrongs.myshoppinglist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.countlesswrongs.myshoppinglist.data.ShopListRepositoryImpl
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.domain.usecase.DeleteShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.EditShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.GetShopItemListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopItemListUseCase.getShopItemList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeShopItemStatus(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }

}
