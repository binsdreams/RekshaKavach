package com.rekshakavach.tracker.domain.entity

data class CartEntity(
    var id: Int? = 0,
    var cart_unique_id: String? = "",
    var agent_id: Int? = 0,
    var user_id: Int? = 0,
    var cart_step_progress: Int? = 0,
    var cart_status: String? = "",
    var payment_mode: String? = "",
    var express_delivery_charges: String? = "",
    var cart_total_price: String? = "",
    var cart_discount: String? = "",
    var convenience_fee: String? = "",
    var vat: String? = "",
    var cart_net_price: String? = "",
    var pickup_time: String? = "",
    var delivery_time: String? = "",
    var express_delivery: Int? = 0,
    var created_at: String? = "",
    var updated_at: String? = "",
    var agent_details: AgentEntity? = null,
    var user_pickup_address: AddressEntity? = null,
    var user_delivery_address: AddressEntity? = null,
    var user_cart_item: List<CartItemEntity>? = null
)