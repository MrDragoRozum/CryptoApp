package com.example.cryptoapp.di

import android.content.Context
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)
    }
}