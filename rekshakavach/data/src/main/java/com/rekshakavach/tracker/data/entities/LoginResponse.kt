package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status") var status: Int = 0,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: TokenReponse? = null
)