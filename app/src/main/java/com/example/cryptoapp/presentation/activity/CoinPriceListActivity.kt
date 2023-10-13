package com.example.cryptoapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.entities.CoinPrice
import com.example.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.example.cryptoapp.presentation.fragment.CoinDetailFragment
import com.example.cryptoapp.presentation.viewmodel.ListCoinViewModel

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

    private fun selectOneCoinDetail(it: CoinPrice) {
        if (isOnePaneMode()) {
            launchActivityCoinDetail(it)
        } else {
            launchFragmentCoinDetail(it)
        }
    }

    private fun launchActivityCoinDetail(it: CoinPrice) {
        val intent = CoinDetailActivity.newIntent(this, it.fromSymbol)
        startActivity(intent)
    }

    private fun launchFragmentCoinDetail(it: CoinPrice) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainerViewCoinDetail,
                CoinDetailFragment.newInstance(it.fromSymbol)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode() = binding.fragmentContainerViewCoinDetail == null
}