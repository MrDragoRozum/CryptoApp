package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.UpdateCoinInfoUseCase

class ListCoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getTopCoinInfoUseCase = GetCoinInfoUseCase(repositoryImpl)
    private val updateCoinInfoUseCase = UpdateCoinInfoUseCase(repositoryImpl)

    val getTopCoinInfo = getTopCoinInfoUseCase.invoke()

    init {
        updateCoinInfoUseCase.invoke()
    }
}