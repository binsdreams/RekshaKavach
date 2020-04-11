package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class AgentResponse(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("agent_name") var agent_name: String? = "",
    @SerializedName("desc") var desc: String? = "",
    @SerializedName("mobile") var mobile: String? = "",
    @SerializedName("media_url") var media_url: String? = "",
    @SerializedName("featured_image_url") var featured_image_url: String? = "",
    @SerializedName("location") var location: String? = ""
)