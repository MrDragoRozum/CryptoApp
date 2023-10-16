package com.example.cryptoapp.presentation.app

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.worker.WorkerFactory
import com.example.cryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var loadDataCoinWorkerFactory: WorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
            .setWorkerFactory(loadDataCoinWorkerFactory)
            .build()

}