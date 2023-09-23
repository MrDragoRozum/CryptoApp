package com.example.cryptoapp.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.data.api.ApiFactory
import com.example.cryptoapp.data.api.ApiHelperImpl
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.dto.CoinPriceInfoDTO
import com.example.cryptoapp.data.dto.CoinPriceInfoRawDataDTO
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(application: Application) : CoinRepository {

    private val apiHelperImpl = ApiHelperImpl(ApiFactory.apiService)
    private val db = AppDatabase.getInstance(application)
    private val mapper = CoinMapper()

    override fun getCoinsList(): LiveData<List<CoinPriceInfo>> =
        db.coinPriceInfoDao().getPriceList().map {
            it.map { mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it) }
        }

    override fun getPriceAboutCoin(fSym: String): LiveData<CoinPriceInfo> =
        db.coinPriceInfoDao().getPriceAboutCoin(fSym).map {
            mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it)
        }

    override suspend fun loadDataFromServer() {
        apiHelperImpl.getTopCoinsInfo()
            .flowOn(Dispatchers.IO)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "null" }
            .flatMapConcat { apiHelperImpl.getFullPriceList(it) }
            .map { getPriceListFromRawData(it) }
            .catch { Log.e("TEST", "Ничего не прилетело, лопух: ${it.message}") }
            .collect {
                Log.d("TEST", "Success in database: $it")
                db.coinPriceInfoDao().insertPriceList(it.map {
                        mapper.mapCoinPriceInfoDOTToCoinPriceInfoDbModel(it)
                    })
            }
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawDataDTO): List<CoinPriceInfoDTO> {
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

