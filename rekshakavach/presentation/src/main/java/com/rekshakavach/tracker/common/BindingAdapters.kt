package com.rekshakavach.tracker.common

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter



@BindingAdapter("app:srcVector")
fun setSrcVector(view: AppCompatImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}

fun getValidString(input :String?):String{
    if(input.isNullOrEmpty()){
        return ""
    }
    return input
}

