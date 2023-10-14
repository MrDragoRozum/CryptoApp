package com.example.cryptoapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun activityComponentFactory(): ActivityComponent.ActivityComponentFactory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}