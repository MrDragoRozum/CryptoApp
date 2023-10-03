package com.example.cryptoapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.entities.CoinPrice

class CoinInfoAdapter : ListAdapter<CoinPrice, CoinInfoViewHolder>(CoinItemDiffCallback()) {

    var onCoinClickListener: ((coin: CoinPrice) -> (Unit))? = null

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
        with(holder.binding) {
            with(coin) {
                val symbolsTemplate =
                    root.context.resources.getString(R.string.symbols_template)
                val lastUpdatedTemplate =
                    root.context.resources.getString(R.string.last_updated_template)

                textViewSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                textViewPrice.text = price
                textViewUpdated.text = String.format(lastUpdatedTemplate, lastUpdate)
                Glide.with(root.context)
                    .load(imageUrl)
                    .into(imageViewLogoCoin)

                root.setOnClickListener {
                    onCoinClickListener?.invoke(this)
                }
            }
        }
    }
}