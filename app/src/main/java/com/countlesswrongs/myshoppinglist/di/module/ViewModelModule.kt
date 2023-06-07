package com.countlesswrongs.myshoppinglist.di.module

import androidx.lifecycle.ViewModel
import com.countlesswrongs.myshoppinglist.di.key.ViewModelKey
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.MainViewModel
import com.countlesswrongs.myshoppinglist.presentation.viewmodel.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}
