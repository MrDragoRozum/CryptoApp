package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    private val apiService = ApiFactory.apiService

    var priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailCoin(fSym: String): LiveData<CoinPriceInfo> =
        db.coinPriceInfoDao().getPriceAboutCoin(fSym)

    init {
        loadData()
    }

    companion object {
        private const val TAG = "CoinViewModel"
    }
    
    private fun loadData() {
        val compositeOne = apiService.getTopCoinsInfo(coinsList = 10)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "null" }
            .flatMap { apiService.getFullPriceList(it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .retry()
            .repeat()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d(TAG, "Success in database: $it")
            },
                { Log.e(TAG, "Ничего не прилетело, лопух: ${it.message}") })
        compositeDisposable.add(compositeOne)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo> {
        val result = mutableListOf<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoRawDataJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKeys in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKeys)
            val currencySet = currencyJson.keySet()
            for (currencyKey in currencySet) {
                val priceInfo = Gson().fromJson(currencyJson.getAsJsonObject(currencyKey), CoinPriceInfo::class.java)
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