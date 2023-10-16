package com.example.cryptoapp.presentation.app

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiHelperImpl
import com.example.cryptoapp.data.worker.LoadDataCoinWorkerFactory
import com.example.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(applicationContext)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(
            LoadDataCoinWorkerFactory(
                ApiHelperImpl(ApiFactory.apiService),
                AppDatabase.getInstance(applicationContext),
                CoinMapper()
            )
        ).build()
    }
}