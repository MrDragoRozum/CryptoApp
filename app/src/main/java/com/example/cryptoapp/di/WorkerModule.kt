package com.example.cryptoapp.di

import com.example.cryptoapp.data.worker.ChildWorkerFactory
import com.example.cryptoapp.data.worker.LoadDataCoinWork
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(LoadDataCoinWork::class)
    fun bindLoadDataCoinWorkFactory(impl: LoadDataCoinWork.Factory): ChildWorkerFactory
}