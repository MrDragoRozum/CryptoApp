package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.dbmodel.CoinInfoDbModel
import com.example.cryptoapp.data.network.dto.CoinInfoDto
import com.example.cryptoapp.data.network.dto.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.dto.CoinNamesListDto
import com.example.cryptoapp.domain.entities.CoinPrice
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinPrice {
        val formattedTime = convertTimestampToTime(dbModel.lastUpdate)
        return CoinPrice(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            formattedPrice(dbModel.price),
            formattedTime,
            formattedPrice(dbModel.highDay),
            formattedPrice(dbModel.lowDay),
            formattedPrice(dbModel.lastMarket),
            dbModel.imageUrl
        )
    }

    private fun formattedPrice(price: String?) = String.format("%.12s", price)

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        dto.lastMarket,
        BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKeys in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKeys)
            val currencySet = currencyJson.keySet()
            for (currencyKey in currencySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto) =
        namesListDto.names?.map { it.coinName?.name }?.joinToString(",") ?: EMPTY_RESULT

    private fun convertTimestampToTime(timestamp: Long?): String {
        timestamp ?: return EMPTY_RESULT
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        private const val EMPTY_RESULT = ""
        private const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}