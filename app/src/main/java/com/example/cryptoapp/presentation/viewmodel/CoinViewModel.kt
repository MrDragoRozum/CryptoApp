package com.example.cryptoapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    getCoinInfoListUseCase: GetCoinInfoListUseCase,
    fSym: String
) : ViewModel() {

    val getDetailInfo = getCoinInfoListUseCase(fSym)
}