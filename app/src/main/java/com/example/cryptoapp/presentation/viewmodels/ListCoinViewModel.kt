package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetTopCoinsInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataFromServerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListCoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getTopCoinInfoUseCase = GetTopCoinsInfoUseCase(repositoryImpl)
    private val loadDataFromServerUseCase = LoadDataFromServerUseCase(repositoryImpl)

    val getTopCoinInfo = getTopCoinInfoUseCase.invoke()
    init {
        loadDataFromServerUseCase.invoke()
    }
}