package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("id") var id:Int? = 0,
    @SerializedName("user_key") var user_key: String? = "",
    @SerializedName("first_name") var first_name: String? = "",
    @SerializedName("last_name") var last_name: String? = "",
    @SerializedName("gender") var gender: String? = "",
    @SerializedName("dob") var dob: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("email") var email: String? = "",
    @SerializedName("mobile") var mobile: String? = "",

    @SerializedName("city_id") var city_id: String? = "",
    @SerializedName("country_id") var country_id: Int? = 0,
    @SerializedName("subscription_enabled") var subscription_enabled: Int? = 0,
    @SerializedName("otp_validated") var otp_validated: Int? = 0,
    @SerializedName("email_verified_at") var email_verified_at: String? = "",
    @SerializedName("created_at") var created_at :String? = "",
    @SerializedName("user_address") var user_address : List<AddressResponse>? =null
)