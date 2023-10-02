package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.dbmodel.CoinInfoDbModel
import com.example.cryptoapp.data.mapper.utils.convertTimestampToTime
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.dto.CoinInfoDto
import com.example.cryptoapp.data.network.dto.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.dto.CoinNamesListDto
import com.example.cryptoapp.domain.entities.CoinPrice
import com.google.gson.Gson

class CoinMapper {
    // TODO: Переименовать методы
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinPrice {
        val fullImageUrl = ApiFactory.BASE_IMAGE_URL + dbModel.imageUrl
        val formattedTime = convertTimestampToTime(dbModel.lastUpdate)
        return CoinPrice(
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

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        dto.lastMarket,
        dto.imageUrl
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
        namesListDto.names?.map { it.coinName?.name }?.joinToString(",") ?: "null"
}