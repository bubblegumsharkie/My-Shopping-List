package com.countlesswrongs.myshoppinglist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.countlesswrongs.myshoppinglist.data.ShopListRepositoryImpl
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.domain.usecase.DeleteShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.EditShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.GetShopItemListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    val shopList = getShopItemListUseCase.getShopItemList()

    fun deleteShopItem(shopItem: ShopItem) {
        scope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeShopItemStatus(shopItem: ShopItem) {
        scope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}
