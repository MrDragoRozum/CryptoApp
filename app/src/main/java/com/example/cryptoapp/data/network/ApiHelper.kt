package com.example.cryptoapp.data.network

import com.example.cryptoapp.data.network.dto.CoinNamesListDto
import com.example.cryptoapp.data.network.dto.CoinInfoJsonContainerDto
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getTopCoinsInfo(coinsList: Int = 10): Flow<CoinNamesListDto>
    fun getFullPriceList(fSym: String): Flow<CoinInfoJsonContainerDto>
}