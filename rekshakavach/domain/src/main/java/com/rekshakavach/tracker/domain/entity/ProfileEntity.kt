package com.rekshakavach.tracker.domain.entity

data class ProfileEntity(
    var status: Int? = 0,
    var message: String? = "",
    var id:Int? = 0,
    var user_key: String? = "",
    var first_name: String? = "",
    var last_name: String? = "",
    var gender: String? = "",
    var dob: String? = "",
    var name: String? = "",
    var email: String? = "",
    var mobile: String? = "",
    var city_id: String? = "",
    var country_id: Int? = 0,
    var subscription_enabled: Int? = 0,
    var otp_validated: Int? = 0,
    var email_verified_at: String? = "",
    var user_address : List<AddressEntity>? =null
)