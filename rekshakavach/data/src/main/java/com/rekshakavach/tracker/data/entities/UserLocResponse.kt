package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserLocResponse(
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("device_id") var device_id: Int? = 0,
    @SerializedName("visited_time") var visited_time: String? = "",
    @SerializedName("geohash") var geohash: String? = ""
)
