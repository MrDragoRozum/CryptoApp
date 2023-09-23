package com.example.cryptoapp.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.R
import com.example.cryptoapp.data.CoinRepositoryImpl
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.entities.CoinPriceInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.presentation.fragments.CoinDetailFragment
import com.example.cryptoapp.presentation.viewmodels.ListCoinViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        // TODO: Тестовый код, перенести в WorkManager
        val repositoryImpl = CoinRepositoryImpl(application)

        lifecycleScope.launch(Dispatchers.IO) {
            repeat(500) {
                delay(3000)
                repositoryImpl.loadDataFromServer()
            }
        }

        binding.adapter = adapter

        viewModel.getTopCoinInfo.observe(this) { adapter.submitList(it) }

        adapter.onCoinClickListener = {
            if (isOnePaneMode()) {
                launchActivityCoinDetail(it)
            } else {
                launchFragmentCoinDetail(it)
            }
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