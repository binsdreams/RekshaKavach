package com.rekshakavach.tracker.common

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message :String,colorRes :Int){
    val snackBarView = Snackbar.make(this, message , Snackbar.LENGTH_LONG)
    val view = snackBarView.view
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    val textView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.WHITE)
    textView.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD)
    view.setBackgroundColor(ContextCompat.getColor(context,colorRes)) // for custom background
    snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackBarView.show()
}

fun Context.getColorRes(resourceID :Int) :Int{
    return ContextCompat.getColor(this,resourceID)
}