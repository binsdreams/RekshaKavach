package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("profile_id") var profile_id: Int? = 0,
    @SerializedName("address_type") var address_type: String? = "",
    @SerializedName("default") var default: Int? = 0,
    @SerializedName("display_name") var display_name: String? = "",
    @SerializedName("mobile") var mobile: String? = "",
    @SerializedName("building_name") var building_name: String? = "",
    @SerializedName("street_name") var street_name: String? = "",
    @SerializedName("landmark") var landmark: String? = "",
    @SerializedName("city_id") var city_id: String? = "",
    @SerializedName("city_name") var city_name: String? = "",
    @SerializedName("pin_code") var pin_code: String? = "",
    @SerializedName("country_id") var country_id: Int? = 0,
    @SerializedName("lat") var lat: Double? = 0.0,
    @SerializedName("long") var long: Double? = 0.0,
    @SerializedName("created_at") var created_at :String? = "",
    @SerializedName("updated_at") var updated_at : String? = ""
)