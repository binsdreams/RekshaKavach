package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("user_id") var user_id: String? = "",
    @SerializedName("phone") var phone: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("registered_date") var registered_date: String? = "",
    @SerializedName("sex") var sex: String? = "",
    @SerializedName("address") var address: String? = "",
    @SerializedName("covid_band") var covid_band: String? = "",
    @SerializedName("code") var code: Int? = 0,
    @SerializedName("message") var message: String? = "",
    @SerializedName("errors") var errors: Array<String?> = emptyArray()
)