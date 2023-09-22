package com.example.cryptoapp.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.presentation.viewmodels.CoinViewModel

class CoinPriceListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter()
        binding.adapter = adapter

        val repositoryImpl = CoinRepositoryImpl(application)
        repositoryImpl.getCoinsList().observe(this) { adapter.coinInfoList = it }
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