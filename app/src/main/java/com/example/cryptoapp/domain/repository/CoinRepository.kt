package com.example.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.entities.CoinPriceInfo

interface CoinRepository {
    fun getCoinsList(): LiveData<List<CoinPriceInfo>>
    fun getPriceAboutCoin(fSym: String): LiveData<CoinPriceInfo>
}