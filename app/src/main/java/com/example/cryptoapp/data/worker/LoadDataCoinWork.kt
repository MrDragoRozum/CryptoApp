package com.example.cryptoapp.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadDataCoinWork(
    context: Context,
    workerParameters: WorkerParameters,
    private val apiHelperImpl: ApiHelper,
    private val db: AppDatabase,
    private val mapper: CoinMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            apiHelperImpl.getTopCoinsInfo(30)
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

    class Factory @Inject constructor(
        private val apiHelperImpl: ApiHelper,
        private val db: AppDatabase,
        private val mapper: CoinMapper
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ) = LoadDataCoinWork(
            context,
            workerParameters,
            apiHelperImpl,
            db,
            mapper
        )
    }
}