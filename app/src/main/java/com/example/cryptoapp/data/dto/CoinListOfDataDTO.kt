package com.example.cryptoapp.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinListOfDataDTO(
    @SerializedName("Data")
    @Expose
    val data: List<CoinDTO>? = null
)
