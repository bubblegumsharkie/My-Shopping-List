package com.countlesswrongs.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.countlesswrongs.myshoppinglist.data.dao.ShopListDao
import com.countlesswrongs.myshoppinglist.data.utils.ShopListMapper
import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import com.countlesswrongs.myshoppinglist.domain.model.ShopItem
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListDao.getShopList().map {
            mapper.mapListOfDbModelToListOfEntity(it)
        }

//  Cool thing to work with
//        MediatorLiveData<List<ShopItem>>().apply {
//            addSource(shopListDao.getShopList()) {
//                value = mapper.mapListOfDbModelToListOfEntity(it)
//            }
//        }
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopListDao.getShopItem(shopItemId))
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

}
