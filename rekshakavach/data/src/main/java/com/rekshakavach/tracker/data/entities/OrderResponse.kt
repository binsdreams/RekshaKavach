package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("order_unique_id") var order_unique_id: String? = "",
    @SerializedName("agent_id") var agent_id: Int? = 0,
    @SerializedName("user_id") var user_id: Int? = 0,
    @SerializedName("order_status") var order_status: String? = "",
    @SerializedName("order_total_price") var order_total_price: String? = "",
    @SerializedName("created_at") var created_at: String? = "",
    @SerializedName("updated_at") var updated_at: String? = "",
    @SerializedName("agent_details") var agent_details: AgentResponse? = null,
    @SerializedName("pickup_time") var pickup_time: String? = "",
    @SerializedName("delivery_time") var delivery_time: String? = "",
    @SerializedName("express_delivery") var express_delivery: Int? = 0
)