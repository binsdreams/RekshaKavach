package com.rekshakavach.tracker.domain.entity

import java.io.Serializable

data class CategoryEntity(
    var id: Int? = 0,
    var category_name: String? = "",
    var parent_category_id: Int? = 0,
    var url: String? = "",
    var subCategories: List<CategoryEntity>? = null,
    var services: List<ServiceEntity>? = null
) : Serializable