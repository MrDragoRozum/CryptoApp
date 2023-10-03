package com.example.cryptoapp.presentation.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageBind(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).into(imageView)
}