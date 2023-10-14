package com.example.cryptoapp

import android.app.Application
import com.example.cryptoapp.di.DaggerApplicationComponent

class CoinApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(applicationContext)
    }
}