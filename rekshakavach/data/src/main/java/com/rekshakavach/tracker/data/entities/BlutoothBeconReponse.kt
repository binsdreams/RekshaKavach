package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class BlutoothBeconReponse(
    @SerializedName("mac") var mac: Int? = 0,
    @SerializedName("name") var name: Int? = 0,
    @SerializedName("description") var description: String? = "",
    @SerializedName("lat") var lat: Int? = 0,
    @SerializedName("lng") var lng: Int? =0
)
