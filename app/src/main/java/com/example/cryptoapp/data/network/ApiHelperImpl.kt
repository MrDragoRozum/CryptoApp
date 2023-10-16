package com.example.cryptoapp.data.network

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getTopCoinsInfo(coinsList: Int) =
        flow { emit(apiService.getCoinNamesList(coinsList)) }

    override fun getFullPriceList(fSym: String) =
        flow { emit(apiService.getCoinInfoJsonContainer(fSym)) }
}