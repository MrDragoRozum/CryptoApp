package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.entities.CoinPriceInfo

class CoinItemDiffCallback : DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo) =
        oldItem.fromSymbol == newItem.fromSymbol

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo) =
        oldItem == newItem
}