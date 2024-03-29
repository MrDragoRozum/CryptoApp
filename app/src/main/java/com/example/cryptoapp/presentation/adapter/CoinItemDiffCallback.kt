package com.example.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.entities.CoinPrice

object CoinItemDiffCallback : DiffUtil.ItemCallback<CoinPrice>() {
    override fun areItemsTheSame(oldItem: CoinPrice, newItem: CoinPrice) =
        oldItem.fromSymbol == newItem.fromSymbol

    override fun areContentsTheSame(oldItem: CoinPrice, newItem: CoinPrice) =
        oldItem == newItem
}