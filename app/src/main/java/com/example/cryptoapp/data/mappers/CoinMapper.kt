package com.example.cryptoapp.data.mappers

import com.example.cryptoapp.data.api.ApiFactory
import com.example.cryptoapp.data.dbmodel.CoinPriceInfoDbModel
import com.example.cryptoapp.data.dto.CoinPriceInfoDTO
import com.example.cryptoapp.data.dto.CoinPriceInfoRawDataDTO
import com.example.cryptoapp.data.utils.convertTimestampToTime
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.google.gson.Gson

class CoinMapper {
    fun mapCoinPriceInfoDbModelToCoinPriceInfo(dbModel: CoinPriceInfoDbModel): CoinPriceInfo {
        val fullImageUrl = ApiFactory.BASE_IMAGE_URL + dbModel.imageUrl
        val formattedTime = convertTimestampToTime(dbModel.lastUpdate)
        return CoinPriceInfo(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            formattedPrice(dbModel.price),
            formattedTime,
            formattedPrice(dbModel.highDay),
            formattedPrice(dbModel.lowDay),
            formattedPrice(dbModel.lastMarket),
            fullImageUrl
        )
    }

    private fun formattedPrice(price: String?) = String.format("%.12s", price)

    fun mapCoinPriceInfoDOTToCoinPriceInfoDbModel(dto: CoinPriceInfoDTO) = CoinPriceInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        dto.lastMarket,
        dto.imageUrl
    )

    fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawDataDTO): List<CoinPriceInfoDTO> {
        val result = mutableListOf<CoinPriceInfoDTO>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoRawDataJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKeys in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKeys)
            val currencySet = currencyJson.keySet()
            for (currencyKey in currencySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfoDTO::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }
}