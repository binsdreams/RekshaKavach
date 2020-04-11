package com.rekshakavach.tracker.domain.entity

data class CartItemEntity(
    var id: Int? = 0,
    var cart_id: Int? = 0,
    var cloth_type_id: Int? = 0,
    var user_id: Int? = 0,
    var service_id: Int? = 0,
    var cloth_count: Int? = 0,
    var cloth_charge_per_piece: Int? = 0,
    var discount_per_piece: Int? = 0,
    var cloth_total_price: Int? = 0,
    var net_price: Int? = 0,
    var cloth_type_details: CategoryEntity? = null,
    var service_details: ServiceEntity? = null
)