package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private lateinit var textViewPriceDetail: TextView
    private lateinit var textViewMinForDay: TextView
    private lateinit var textViewMaxForDay: TextView
    private lateinit var textViewLastDeal: TextView
    private lateinit var textViewLastUpdated: TextView
    private lateinit var imageViewLogoCoinDetail: ImageView
    private lateinit var textViewCoinDetail: TextView
    private lateinit var textViewCurrency: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        init()
        intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
            viewModel.getDetailCoin(it).observe(this@CoinDetailActivity) {
                textViewCoinDetail.text = it.fromSymbol
                textViewCurrency.text = it.toSymbol
                textViewPriceDetail.text = it.price
                textViewMinForDay.text = it.lowDay
                textViewMaxForDay.text = it.highDay
                textViewLastDeal.text = it.lastMarket
                textViewLastUpdated.text = it.getFormattedTime()
                Glide.with(this@CoinDetailActivity)
                    .load(it.getFullImageUrl())
                    .into(imageViewLogoCoinDetail)
            }
        }
    }

    private fun init() {
        textViewPriceDetail = findViewById(R.id.textViewPriceDetail)
        textViewMinForDay = findViewById(R.id.textViewMinForDay)
        textViewMaxForDay = findViewById(R.id.textViewMaxForDay)
        textViewLastDeal = findViewById(R.id.textViewLastDeal)
        textViewLastUpdated = findViewById(R.id.textViewLastUpdated)
        imageViewLogoCoinDetail = findViewById(R.id.imageViewLogoCoinDetail)
        textViewCoinDetail = findViewById(R.id.textViewCoinDetail)
        textViewCurrency = findViewById(R.id.textViewCurrency)
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"
        fun newIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fSym)
            return intent
        }
    }
}