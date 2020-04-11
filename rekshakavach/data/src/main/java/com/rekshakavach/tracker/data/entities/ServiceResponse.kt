package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class ServiceResponse (
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("service_name") var service_name: String? = "",
    @SerializedName("desc") var desc: String? = ""
)