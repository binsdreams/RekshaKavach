package com.rekshakavach.tracker.domain.entity

data class OrderListEntity (
    var status: Int? = 0,
    var message: String? = "",
    var ordersList : List<OrderEntity>? = null
)