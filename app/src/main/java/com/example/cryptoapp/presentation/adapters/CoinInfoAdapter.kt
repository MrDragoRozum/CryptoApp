package com.example.cryptoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.domain.entities.CoinPriceInfo

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = itemView.context.resources.getString(R.string.symbols_template)
                val lastUpdatedTemplate = itemView.context.resources.getString(R.string.last_updated_template)
                textViewSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                textViewPrice.text = price
//                textViewUpdated.text = String.format(lastUpdatedTemplate, getFormattedTime())
//                Glide.with(itemView.context)
//                    .load(getFullImageUrl())
//                    .into(imageViewLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    inner class CoinInfoViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageViewLogoCoin = itemView.findViewById<ImageView>(R.id.imageViewLogoCoin)
        val textViewSymbols = itemView.findViewById<TextView>(R.id.textViewSymbols)
        val textViewPrice = itemView.findViewById<TextView>(R.id.textViewPrice)
        val textViewUpdated = itemView.findViewById<TextView>(R.id.textViewUpdated)

    }

    interface OnCoinClickListener {
        fun onCoinClick(coin: CoinPriceInfo)
    }
}