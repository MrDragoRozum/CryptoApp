package com.example.cryptoapp.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.presentation.fragments.CoinDetailFragment

class CoinDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        savedInstanceState ?: startFragment()
    }

    private fun startFragment() {
        intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainerViewCoinDetailActivity,
                    CoinDetailFragment.newInstance(it)
                )
                .commit()
        }

    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"
        fun newIntent(context: Context, fSym: String) =
            Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, fSym)
            }
    }
}