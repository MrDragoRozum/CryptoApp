package com.example.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.presentation.viewmodel.CoinViewModel
import com.example.cryptoapp.presentation.viewmodel.ListCoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModel {

    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindCoinViewModel(impl: CoinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListCoinViewModel::class)
    fun bindListCoinViewModel(impl: ListCoinViewModel): ViewModel

}