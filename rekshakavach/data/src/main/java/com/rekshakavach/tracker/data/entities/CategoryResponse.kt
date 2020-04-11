package com.rekshakavach.tracker.data.entities

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("category_name") var category_name: String? = "",
    @SerializedName("parent_category_id") var parent_category_id: Int? = 0,
    @SerializedName("url") var url: String? = "",
    @SerializedName("sub") var sub: ArrayList<CategoryResponse>? = null
)