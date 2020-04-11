package com.rekshakavach.tracker.domain.entity

import java.io.Serializable

class ServiceEntity(
    var id: Int? = 0,
    var service_name: String? = "",
    var desc: String? = ""
) : Serializable