package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class UserCovidDiagnosisDetailsResponse(
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("isPositive") var isPositive: Int? = 0,
    @SerializedName("positive_declaration_date") var positive_declaration_date: String? = "",
    @SerializedName("isDischarged") var isDischarged: Int? = 0,
    @SerializedName("discharge_date") var discharge_date: Int? =0
)
