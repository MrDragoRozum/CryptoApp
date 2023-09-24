package com.example.cryptoapp.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.presentation.fragments.CoinDetailFragment
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

        supportFragmentManager.popBackStack()

        viewModel.getTopCoinInfo.observe(this) { adapter.submitList(it) }

        adapter.onCoinClickListener = {
            selectOneCoinDetail(it)
        }
    }

    private fun selectOneCoinDetail(it: CoinPriceInfo) {
        if (isOnePaneMode()) {
            launchActivityCoinDetail(it)
        } else {
            launchFragmentCoinDetail(it)
        }
    }

    private fun launchActivityCoinDetail(it: CoinPriceInfo) {
        val intent = CoinDetailActivity.newIntent(this, it.fromSymbol)
        startActivity(intent)
    }

    private fun launchFragmentCoinDetail(it: CoinPriceInfo) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainerViewCoinDetail,
                CoinDetailFragment.newInstance(it.fromSymbol)
            )
            .addToBackStack(null).commit()
    }

    private fun isOnePaneMode() = binding.fragmentContainerViewCoinDetail == null
}