package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.entities.CoinPrice

class CoinItemDiffCallback : DiffUtil.ItemCallback<CoinPrice>() {
    override fun areItemsTheSame(oldItem: CoinPrice, newItem: CoinPrice) =
        oldItem.fromSymbol == newItem.fromSymbol

    override fun areContentsTheSame(oldItem: CoinPrice, newItem: CoinPrice) =
        oldItem == newItem
}