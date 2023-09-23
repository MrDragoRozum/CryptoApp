package com.example.cryptoapp.data.api

import com.example.cryptoapp.data.dto.CoinListOfDataDTO
import com.example.cryptoapp.data.dto.CoinPriceInfoRawDataDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_LIMIT) coinsList: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): CoinListOfDataDTO

    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_TO_COIN_SYMBOL) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): CoinPriceInfoRawDataDTO

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