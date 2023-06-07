package com.countlesswrongs.myshoppinglist.presentation

import android.app.Application
import com.countlesswrongs.myshoppinglist.di.component.DaggerApplicationComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}
