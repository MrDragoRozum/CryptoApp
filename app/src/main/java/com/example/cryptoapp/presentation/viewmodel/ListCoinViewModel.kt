package com.example.cryptoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadCoinInfoUseCase
import javax.inject.Inject

class ListCoinViewModel @Inject constructor(
    getCoinInfoUseCase: GetCoinInfoUseCase,
    loadCoinInfoUseCase: LoadCoinInfoUseCase
) : ViewModel() {

    val getTopCoinInfo = getCoinInfoUseCase.invoke()

    init {
        loadCoinInfoUseCase.invoke()
    }
}