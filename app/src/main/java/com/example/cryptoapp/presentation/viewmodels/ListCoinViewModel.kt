package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetTopCoinsInfoUseCase

class ListCoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getTopCoinInfoUseCase = GetTopCoinsInfoUseCase(repositoryImpl)

    val getTopCoinInfo = getTopCoinInfoUseCase.invoke()
}