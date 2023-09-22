package com.example.cryptoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.entities.CoinPriceInfo

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
//        val view = LayoutInflater
//            .from(parent.context)
//            .inflate(R.layout.item_coin_info, parent, false)
        val view = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(binding) {
                with(coin) {
                    val symbolsTemplate =
                        itemView.context.resources.getString(R.string.symbols_template)
                    val lastUpdatedTemplate =
                        itemView.context.resources.getString(R.string.last_updated_template)
                    textViewSymbols.text =
                        String.format(symbolsTemplate, fromSymbol, toSymbol)
                    textViewPrice.text = price
                    textViewUpdated.text = String.format(lastUpdatedTemplate, lastUpdate)
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .into(imageViewLogoCoin)
                    itemView.setOnClickListener {
                        onCoinClickListener?.onCoinClick(this)
                    }
                }
            }
        }
    }

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) : ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coin: CoinPriceInfo)
    }
}