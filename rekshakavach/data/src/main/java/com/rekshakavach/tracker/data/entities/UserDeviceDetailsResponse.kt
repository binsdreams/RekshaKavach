package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserDeviceDetailsResponse(
    @SerializedName("device_id") var device_id: Int? = 0,
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("device_make") var device_make: String? = ""
)