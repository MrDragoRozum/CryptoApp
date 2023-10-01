package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase

class CoinViewModel(
    application: Application,
    fSym: String
) : ViewModel() {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repositoryImpl)

    val getDetailInfo = getCoinInfoListUseCase(fSym)
}