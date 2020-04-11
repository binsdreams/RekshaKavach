package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class TokenReponse(
    @SerializedName("token") var token: String? = null
)