package com.example.cryptoapp.presentation.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    Log.d("Adapter", "loadImage: $url")
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}