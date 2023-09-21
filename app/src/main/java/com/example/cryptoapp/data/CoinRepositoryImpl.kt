package com.example.cryptoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository

class CoinRepositoryImpl(application: Application) : CoinRepository {

    private val db = AppDatabase.getInstance(application)
    private val mapper = CoinMapper()
    override fun getCoinsList(): LiveData<List<CoinPriceInfo>> =
        Transformations.map(db.coinPriceInfoDao().getPriceList()) {
            it.map { mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it) }
        }

    override fun getPriceAboutCoin(fSym: String): LiveData<CoinPriceInfo> =
        Transformations.map(db.coinPriceInfoDao().getPriceAboutCoin(fSym)) {
            mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it)
        }
}