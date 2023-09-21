package com.example.cryptoapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.api.ApiFactory
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.dto.CoinPriceInfoDTO
import com.example.cryptoapp.data.dto.CoinPriceInfoRawDataDTO
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    init {
        loadData()
    }

    companion object {
        private const val TAG = "CoinViewModel"
    }

    // Тестовыый код, данные прилетают нормально

    private fun loadData() {
        val compositeOne = apiService.getTopCoinsInfo(coinsList = 25)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "null" }
            .flatMap { apiService.getFullPriceList(it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .retry()
            .repeat()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it.map {
                    mapper.mapCoinPriceInfoDOTToCoinPriceInfoDbModel(it)
                })
                Log.d(TAG, "Success in database: $it")
            },
                { Log.e(TAG, "Ничего не прилетело, лопух: ${it.message}") })
        compositeDisposable.add(compositeOne)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}