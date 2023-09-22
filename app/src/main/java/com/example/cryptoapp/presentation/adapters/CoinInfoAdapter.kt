package com.example.cryptoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.entities.CoinPriceInfo

class CoinInfoAdapter : ListAdapter<CoinPriceInfo, CoinInfoViewHolder>(CoinItemDiffCallback()) {

    var onCoinClickListener: ((coin: CoinPriceInfo) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder) {
            with(binding) {
                with(coin) {
                    val symbolsTemplate =
                        itemView.context.resources.getString(R.string.symbols_template)
                    val lastUpdatedTemplate =
                        itemView.context.resources.getString(R.string.last_updated_template)

                    textViewSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                    textViewPrice.text = price
                    textViewUpdated.text = String.format(lastUpdatedTemplate, lastUpdate)
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .into(imageViewLogoCoin)

                    itemView.setOnClickListener {
                        this@CoinInfoAdapter.onCoinClickListener?.invoke(this)
                    }
                }
            }
        }
    }
}