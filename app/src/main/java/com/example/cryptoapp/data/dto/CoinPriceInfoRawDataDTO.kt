package com.example.cryptoapp.data.dto

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRawDataDTO(
    @SerializedName("RAW")
    @Expose
    val coinPriceInfoRawDataJsonObject: JsonObject? = null
)
