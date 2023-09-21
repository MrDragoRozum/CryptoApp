package com.example.cryptoapp.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.presentation.viewmodels.CoinViewModel

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        // Тестовый код, всё работает нормально
        val repository = CoinRepositoryImpl(application)
        repository.getCoinsList().observe(this) {
            Log.d("MainActivity", "onCreate: $it")
        }
        viewModel = ViewModelProvider(this) [CoinViewModel::class.java]


//
//        val recyclerViewCoinPriceList = findViewById<RecyclerView>(R.id.recyclerViewCoinPriceList)
//        val adapter = CoinInfoAdapter()
//        viewModel.priceList.observe(this) { adapter.coinInfoList = it }
//        recyclerViewCoinPriceList.adapter = adapter
//        adapter.onCoinClickListener = (object : CoinInfoAdapter.OnCoinClickListener {
//            override fun onCoinClick(coin: CoinPriceInfo) {
//                intent = CoinDetailActivity.newIntent(
//                    this@CoinPriceListActivity,
//                    coin.fromSymbol
//                )
//                startActivity(intent)
//            }
//        })
    }
}