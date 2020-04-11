package com.rekshakavach.tracker.domain.entity

data class OrderEntity(
    var id: Int? = 0,
    var order_unique_id: String? = "",
    var agent_id: Int? = 0,
    var user_id: Int? = 0,
    var order_status: String? = "",
    var order_total_price: String? = "",
    var created_at: String? = "",
    var updated_at: String? = "",
    var agent_details: AgentEntity? = null,
    var pickup_time: String? = "",
    var delivery_time: String? = "",
    var express_delivery: Int? = 0
)