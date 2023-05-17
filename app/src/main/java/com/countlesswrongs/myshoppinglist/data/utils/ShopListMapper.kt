package com.countlesswrongs.myshoppinglist.data.utils

import com.countlesswrongs.myshoppinglist.data.entity.ShopItemDbModel
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem

class ShopListMapper {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        amount = shopItem.amount,
        enabled = shopItem.enabled
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        amount = shopItemDbModel.amount,
        enabled = shopItemDbModel.enabled
    )

    fun mapListOfDbModelToListOfEntity(list: List<ShopItemDbModel>) = list.map {
     mapDbModelToEntity(it)
    }

}

