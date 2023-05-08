package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    private val apiService = ApiFactory.apiService

    private var priceList = db.coinPriceInfoDao().getPriceList()

    companion object {
        private const val TAG = "CoinViewModel"
    }

    fun loadData() {
        val compositeOne = apiService.getTopCoinsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Log.d(TAG, it.toString()) },
                { Log.e(TAG, "Ничего не прилетело, лопух: ${it.message}") })
        compositeDisposable.add(compositeOne)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}