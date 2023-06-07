package com.countlesswrongs.myshoppinglist.di.module

import android.app.Application
import com.countlesswrongs.myshoppinglist.data.ShopListRepositoryImpl
import com.countlesswrongs.myshoppinglist.data.dao.ShopListDao
import com.countlesswrongs.myshoppinglist.data.db.AppDatabase
import com.countlesswrongs.myshoppinglist.di.scope.ApplicationScope
import com.countlesswrongs.myshoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}
