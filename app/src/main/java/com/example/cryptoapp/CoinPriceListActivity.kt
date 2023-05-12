package com.example.cryptoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.adapters.CoinInfoAdapter
import com.example.cryptoapp.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val recyclerViewCoinPriceList = findViewById<RecyclerView>(R.id.recyclerViewCoinPriceList)
        val adapter = CoinInfoAdapter()
        viewModel.priceList.observe(this) { adapter.coinInfoList = it }
        recyclerViewCoinPriceList.adapter = adapter
        adapter.onCoinClickListener = (object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: CoinPriceInfo) {
                intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coin.fromSymbol
                )
                startActivity(intent)
            }
        })
    }
}