package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("cart_unique_id") var cart_unique_id: String? = "",
    @SerializedName("agent_id") var agent_id: Int? = 0,
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("cart_step_progress") var cart_step_progress: Int? = 0,
    @SerializedName("cart_status") var cart_status: String? = "",
    @SerializedName("payment_mode") var payment_mode: String? = "",
    @SerializedName("express_delivery_charges") var express_delivery_charges: String? = "",
    @SerializedName("cart_total_price") var cart_total_price: String? = "",
    @SerializedName("cart_discount") var cart_discount: String? = "",
    @SerializedName("convenience_fee") var convenience_fee: String? = "",
    @SerializedName("vat") var vat: String? = "",
    @SerializedName("cart_net_price") var cart_net_price: String? = "",
    @SerializedName("pickup_time") var pickup_time: String? = "",
    @SerializedName("delivery_time") var delivery_time: String? = "",
    @SerializedName("express_delivery") var express_delivery: Int? = 0,
    @SerializedName("created_at") var created_at: String? = "",
    @SerializedName("updated_at") var updated_at: String? = "",
    @SerializedName("agent_details") var agent_details: AgentResponse? = null,
    @SerializedName("user_pickup_address") var user_pickup_address: AddressResponse? = null,
    @SerializedName("user_delivery_address") var user_delivery_address: AddressResponse? = null,
    @SerializedName("user_cart_item") var user_cart_item: List<CartItemResponse>? = null
)