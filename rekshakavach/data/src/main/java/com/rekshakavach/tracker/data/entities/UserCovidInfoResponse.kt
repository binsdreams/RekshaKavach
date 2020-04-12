package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserCovidInfoResponse(
    @SerializedName("positive_declaration_date") var positive_declaration_date: String? = "",
    @SerializedName("discharge_date") var discharge_date: String? = "",
    @SerializedName("user_id") var user_id: String? = "",
    @SerializedName("is_positive") var is_positive: Boolean? =false,
    @SerializedName("is_discharged") var is_discharged: Boolean? =false
)

