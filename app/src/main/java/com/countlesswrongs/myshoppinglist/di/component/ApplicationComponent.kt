package com.countlesswrongs.myshoppinglist.di.component

import android.app.Application
import com.countlesswrongs.myshoppinglist.di.module.DataModule
import com.countlesswrongs.myshoppinglist.di.module.ViewModelModule
import com.countlesswrongs.myshoppinglist.di.scope.ApplicationScope
import com.countlesswrongs.myshoppinglist.presentation.activity.MainActivity
import com.countlesswrongs.myshoppinglist.presentation.fragment.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
