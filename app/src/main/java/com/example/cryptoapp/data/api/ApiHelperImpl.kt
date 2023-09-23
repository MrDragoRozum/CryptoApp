package com.example.cryptoapp.data.api

import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override fun getTopCoinsInfo(coinsList: Int) = flow { emit(apiService.getTopCoinsInfo(coinsList)) }

    override fun getFullPriceList(fSym: String) = flow { emit(apiService.getFullPriceList(fSym)) }
}