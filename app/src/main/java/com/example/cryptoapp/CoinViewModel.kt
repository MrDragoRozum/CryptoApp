package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    private val apiService = ApiFactory.apiService

    var priceList = db.coinPriceInfoDao().getPriceList()

    companion object {
        private const val TAG = "CoinViewModel"
    }

    fun loadData() {
        val compositeOne = apiService.getTopCoinsInfo(coinsList = 10)
            .subscribeOn(Schedulers.io())
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "null" }
            .flatMap { apiService.getFullPriceList(it) }
            .map { getPriceListFromRawData(it) }
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d(TAG, "Success in database: $it")
            },
                { Log.e(TAG, "Ничего не прилетело, лопух: ${it.message}") })
        compositeDisposable.add(compositeOne)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = mutableListOf<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoRawDataJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson()
                    .fromJson(currencyJson.getAsJsonObject(currencyKey), CoinPriceInfo::class.java)
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