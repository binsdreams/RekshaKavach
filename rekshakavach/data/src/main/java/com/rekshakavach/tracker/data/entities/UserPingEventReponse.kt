package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserPingEventReponse(
    @SerializedName("user") var user: Int? = 0,
    @SerializedName("lat") var lat: Double? = 0.0,
    @SerializedName("lng") var lng: Double? = 0.0,
    @SerializedName("timestamp") var timestamp: Long? = 0,
    @SerializedName("nearbyBluetoothBeacons") var nearbyBluetoothBeacons: ArrayList<NearByBleResponse>?
)
