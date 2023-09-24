package com.example.cryptoapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinMapper
import com.example.cryptoapp.data.workers.LoadDataCoinWork
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.domain.repository.CoinRepository

class CoinRepositoryImpl(private val context: Context) : CoinRepository {

    private val db = AppDatabase.getInstance(context)
    private val mapper = CoinMapper()

    override fun getCoinsList(): LiveData<List<CoinPriceInfo>> =
        db.coinPriceInfoDao().getPriceList().map {
            it.map { mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it) }
        }

    override fun getPriceAboutCoin(fSym: String): LiveData<CoinPriceInfo> =
        db.coinPriceInfoDao().getPriceAboutCoin(fSym).map {
            mapper.mapCoinPriceInfoDbModelToCoinPriceInfo(it)
        }

    override fun loadDataFromServer() {
        WorkManager.getInstance(context).enqueueUniqueWork(
            LoadDataCoinWork.NAME_WORK,
            ExistingWorkPolicy.REPLACE,
            LoadDataCoinWork.makeRequest
        )
    }
}

