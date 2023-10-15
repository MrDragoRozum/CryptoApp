package com.example.cryptoapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.presentation.app.CoinApp
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.entities.CoinPrice
import com.example.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.example.cryptoapp.presentation.fragment.CoinDetailFragment
import com.example.cryptoapp.presentation.viewmodel.ListCoinViewModel
import com.example.cryptoapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ListCoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var adapter: CoinInfoAdapter

    private val component by lazy {
        (application as CoinApp).component
            .activityComponentFactory()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.injectCoinPriceList(this)
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