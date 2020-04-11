package com.rekshakavach.tracker.domain.entity

data class LoginEntity (
    var status: Int = 0,
    var message: String? = null,
    var data: TokenEntity? = null
)