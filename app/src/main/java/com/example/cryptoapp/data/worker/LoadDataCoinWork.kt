package com.example.cryptoapp.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiHelperImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class LoadDataCoinWork(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val apiHelperImpl = ApiHelperImpl(ApiFactory.apiService)
    private val db = AppDatabase.getInstance(context)
    private val mapper = CoinMapper()
    override suspend fun doWork(): Result {
        while (true) {
            apiHelperImpl.getTopCoinsInfo()
                .map { mapper.mapNamesListToString(it) }
                .flatMapConcat { apiHelperImpl.getFullPriceList(it) }
                .map { mapper.mapJsonContainerToListCoinInfo(it) }
                .catch { Log.e(NAME_WORK, "Ничего не прилетело, лопух: ${it.message}") }
                .collect {
                    Log.d(NAME_WORK, "Success in database: $it")
                    db.coinPriceInfoDao().insertPriceList(it.map {
                        mapper.mapDtoToDbModel(it)
                    })
                }
            delay(10000)
        }
    }

    companion object {
        const val NAME_WORK = "Coin Work"
        val makeRequest = OneTimeWorkRequestBuilder<LoadDataCoinWork>().build()
    }
}