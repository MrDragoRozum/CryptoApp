package com.example.cryptoapp.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDTO(
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinNameDTO? = null
)
