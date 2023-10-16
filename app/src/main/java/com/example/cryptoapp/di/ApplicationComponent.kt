package com.example.cryptoapp.di

import android.content.Context
import com.example.cryptoapp.presentation.app.CoinApp
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun activityComponentFactory(): ActivityComponent.ActivityComponentFactory

    fun inject(app: CoinApp)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}