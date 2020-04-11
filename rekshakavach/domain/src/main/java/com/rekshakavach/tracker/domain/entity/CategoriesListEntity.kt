package com.rekshakavach.tracker.domain.entity

data class CategoriesListEntity(
    var status: Int? = 0,
    var message: String? = "",
    var categoriesList: List<CategoryEntity>? = null
)