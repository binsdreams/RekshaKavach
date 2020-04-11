package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class CartItemResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("cart_id") var cart_id: Int? = 0,
    @SerializedName("cloth_type_id") var cloth_type_id: Int? = 0,
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("service_id") var service_id: Int? = 0,
    @SerializedName("cloth_count") var cloth_count: Int? = 0,
    @SerializedName("cloth_charge_per_piece") var cloth_charge_per_piece: Int? = 0,
    @SerializedName("discount_per_piece") var discount_per_piece: Int? = 0,
    @SerializedName("cloth_total_price") var cloth_total_price: Int? = 0,
    @SerializedName("net_price") var net_price: Int? = 0,
    @SerializedName("cloth_type_details") var cloth_type_details: CategoryResponse? = null,
    @SerializedName("service_details") var service_details: ServiceResponse? = null
)