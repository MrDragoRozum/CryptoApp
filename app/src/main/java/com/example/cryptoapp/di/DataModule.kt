package com.example.cryptoapp.di

import android.content.Context
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiHelper
import com.example.cryptoapp.data.network.ApiHelperImpl
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @ApplicationScope
    @Binds
    fun bindApiHelper(impl: ApiHelperImpl): ApiHelper

    companion object {
        @ApplicationScope
        @Provides
        fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)

        @ApplicationScope
        @Provides
        fun provideApiHelper(): ApiService = ApiFactory.apiService
    }
}