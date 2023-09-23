package com.example.cryptoapp.data.api

import com.example.cryptoapp.data.dto.CoinListOfDataDTO
import com.example.cryptoapp.data.dto.CoinPriceInfoRawDataDTO
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getTopCoinsInfo(coinsList: Int = 10): Flow<CoinListOfDataDTO>
    fun getFullPriceList(fSym: String): Flow<CoinPriceInfoRawDataDTO>
}