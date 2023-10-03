package com.example.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.entities.CoinPrice

interface CoinRepository {
    fun getCoinsList(): LiveData<List<CoinPrice>>
    fun getPriceAboutCoin(fromSymbol: String): LiveData<CoinPrice>
    fun loadData()
}