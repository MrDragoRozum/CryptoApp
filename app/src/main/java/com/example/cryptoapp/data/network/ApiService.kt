package com.example.cryptoapp.data.network

import com.example.cryptoapp.data.network.dto.CoinNamesListDto
import com.example.cryptoapp.data.network.dto.CoinInfoJsonContainerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    suspend fun getCoinNamesList(
        @Query(QUERY_PARAM_LIMIT) coinsList: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): CoinNamesListDto

    @GET("pricemultifull")
    suspend fun getCoinInfoJsonContainer(
        @Query(QUERY_PARAM_TO_COIN_SYMBOL) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): CoinInfoJsonContainerDto

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_TO_COIN_SYMBOL = "fsyms"
        private const val QUERY_PARAM_API_KEY = "api_key"

        private const val CURRENCY = "USD"

        private const val API_KEY =
            "97d204d5a46d70510fdcd28112575d950564a3127e1cd0b6e8a63dcb867ec11c"
    }
}