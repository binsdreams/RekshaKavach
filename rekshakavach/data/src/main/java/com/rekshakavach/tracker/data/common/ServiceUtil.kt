package com.rekshakavach.tracker.data.common

import java.text.SimpleDateFormat
import java.util.*

fun parseDateToServerFormat(date :Date):String{
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(date)
}

