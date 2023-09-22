package com.example.cryptoapp.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository

class CoinRepositoryImpl(application: Application) : CoinRepository {

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
}