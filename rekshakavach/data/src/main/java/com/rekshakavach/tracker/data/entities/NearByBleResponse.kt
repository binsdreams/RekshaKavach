package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class NearByBleResponse(
    @SerializedName("mac") var mac: Int? = 0,
    @SerializedName("name") var name: Int? = 0,
    @SerializedName("description") var description: String? = "",
    @SerializedName("lat") var lat: Double? = 0.0,
    @SerializedName("lng") var lng: Double? = 0.0
)
