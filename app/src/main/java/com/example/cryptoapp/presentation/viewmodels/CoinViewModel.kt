package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetFullPriceListUseCase

class CoinViewModel(
    application: Application,
    fSym: String
) : ViewModel() {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getFullPriceListUseCase = GetFullPriceListUseCase(repositoryImpl)

    val getDetailInfo = getFullPriceListUseCase(fSym)
}