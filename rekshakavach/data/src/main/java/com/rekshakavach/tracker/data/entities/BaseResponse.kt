package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("code") var code: Int? = 0,
    @SerializedName("message") var message: String? = ""
)