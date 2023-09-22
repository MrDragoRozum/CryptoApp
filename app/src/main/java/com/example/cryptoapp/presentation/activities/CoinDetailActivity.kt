package com.example.cryptoapp.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.example.cryptoapp.presentation.viewmodels.CoinViewModel
import com.example.cryptoapp.presentation.viewmodels.CoinViewModelFactory

class CoinDetailActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
            CoinViewModelFactory(application, it)
        }!!
    }

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        installData()
    }

    private fun installData() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"
        fun newIntent(context: Context, fSym: String) =
            Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, fSym)
            }
    }
}