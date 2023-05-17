package com.countlesswrongs.myshoppinglist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.countlesswrongs.myshoppinglist.data.ShopListRepositoryImpl
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import com.countlesswrongs.myshoppinglist.domain.usecase.AddShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.EditShopItemUseCase
import com.countlesswrongs.myshoppinglist.domain.usecase.GetShopItemByIdUseCase

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputAmount = MutableLiveData<Boolean>()
    val errorInputAmount: LiveData<Boolean>
        get() = _errorInputAmount

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _currentShopItem = MutableLiveData<ShopItem>()
    val currentShopItem: LiveData<ShopItem>
        get() = _currentShopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _currentShopItem.value = item
    }

    private fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
    }

    fun addShopItem(inputName: String?, inputAmount: String?) {
        resetInputNameError()
        resetInputAmountError()
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        val fieldsValid = validateInput(name, amount)
        if (fieldsValid) {
            val shopItem = ShopItem(name, amount, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputAmount: String?) {
        resetInputNameError()
        resetInputAmountError()
        val name = parseName(inputName)
        val amount = parseAmount(inputAmount)
        val fieldsValid = validateInput(name, amount)
        if (fieldsValid) {
            _currentShopItem.value?.let {
                val shopItem = it.copy(name = name, amount = amount)
                editShopItem(shopItem)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseAmount(inputAmount: String?): Int {
        return try {
            inputAmount?.trim()?.toInt() ?: 0
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            _errorInputAmount.value = true
            return 0
        }
    }

    private fun validateInput(name: String, amount: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true
        }
        if (amount <= 0) {
            result = false
            _errorInputAmount.value = true
        }
        return result
    }

    fun resetInputNameError() {
        _errorInputName.value = false
    }

    fun resetInputAmountError() {
        _errorInputAmount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}
