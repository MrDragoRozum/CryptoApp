package com.example.cryptoapp.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.presentation.viewmodels.ListCoinViewModel

class CoinPriceListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ListCoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        CoinInfoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.adapter = adapter

        viewModel.getTopCoinInfo.observe(this) { adapter.submitList(it) }

        adapter.onCoinClickListener = {
            intent = CoinDetailActivity.newIntent(this, it.fromSymbol)
            startActivity(intent)
        }
    }
}