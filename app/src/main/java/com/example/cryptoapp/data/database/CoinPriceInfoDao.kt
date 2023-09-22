package com.example.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.data.dbmodel.CoinPriceInfoDbModel

@Dao
interface CoinPriceInfoDao {

    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinPriceInfoDbModel>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceAboutCoin(fSym: String): LiveData<CoinPriceInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(list: List<CoinPriceInfoDbModel>)
}