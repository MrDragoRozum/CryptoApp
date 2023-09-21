package com.example.cryptoapp.data.mappers

import com.example.cryptoapp.data.api.ApiFactory
import com.example.cryptoapp.data.utils.convertTimestampToTime
import com.example.cryptoapp.data.dbmodel.CoinPriceInfoDbModel
import com.example.cryptoapp.data.dto.CoinPriceInfoDTO
import com.example.cryptoapp.domain.entities.CoinPriceInfo

class CoinMapper {
    fun mapCoinPriceInfoDbModelToCoinPriceInfo(dbModel: CoinPriceInfoDbModel): CoinPriceInfo {
        val fullImageUrl = ApiFactory.BASE_IMAGE_URL + dbModel.imageUrl
        val formattedTime = convertTimestampToTime(dbModel.lastUpdate)
        return CoinPriceInfo(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            dbModel.price,
            formattedTime,
            dbModel.highDay,
            dbModel.lowDay,
            dbModel.lastMarket,
            fullImageUrl
        )
    }

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
}