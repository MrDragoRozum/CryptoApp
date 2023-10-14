package com.example.cryptoapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.worker.LoadDataCoinWork
import com.example.cryptoapp.domain.entities.CoinPrice
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val context: Context,
    private val mapper: CoinMapper,
    private val db: AppDatabase
) : CoinRepository {
    override fun getCoinsList(): LiveData<List<CoinPrice>> =
        db.coinPriceInfoDao().getPriceList().map {
            it.map { mapper.mapDbModelToEntity(it) }
        }

    override fun getPriceAboutCoin(fromSymbol: String): LiveData<CoinPrice> =
        db.coinPriceInfoDao().getPriceAboutCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }

    override fun loadData() {
        WorkManager.getInstance(context).enqueueUniqueWork(
            LoadDataCoinWork.NAME_WORK,
            ExistingWorkPolicy.REPLACE,
            LoadDataCoinWork.makeRequest
        )
    }
}

