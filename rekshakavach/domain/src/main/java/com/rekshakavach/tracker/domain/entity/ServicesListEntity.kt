package com.rekshakavach.tracker.domain.entity

data class ServicesListEntity(
    var status: Int? = 0,
    var message: String? = "",
    var servicesList: List<ServiceEntity>? = null
)