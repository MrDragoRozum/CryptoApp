package com.example.cryptoapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiHelper

class LoadDataCoinWorkerFactory(
    private val apiHelperImpl: ApiHelper,
    private val db: AppDatabase,
    private val mapper: CoinMapper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return LoadDataCoinWork(
            appContext,
            workerParameters,
            apiHelperImpl,
            db,
            mapper
        )
    }
}