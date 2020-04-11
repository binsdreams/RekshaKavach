package com.rekshakavach.tracker.domain.entity

data class AgentEntity(
    var id: Int? = 0,
    var agent_name: String? = "",
    var desc: String? = "",
    var mobile: String? = "",
    var media_url: String? = "",
    var featured_image_url: String? = "",
    var location: String? = ""
)