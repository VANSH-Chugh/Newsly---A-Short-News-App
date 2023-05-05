package com.example.newsserviceapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageFromUrl")
fun ImageView.loadImageFromUrl(url: String?){
    if (url!=null){
        load(url)
    }
}