package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadCoinInfoUseCase

class ListCoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repositoryImpl)
    private val loadCoinInfoUseCase = LoadCoinInfoUseCase(repositoryImpl)

    val getTopCoinInfo = getCoinInfoUseCase.invoke()

    init {
        loadCoinInfoUseCase.invoke()
    }
}