package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.BaseResponse
import com.rekshakavach.tracker.domain.entity.BaseEntity

class BaseMapper {

    fun map(response :BaseResponse): BaseEntity =
        BaseEntity(
            code =response.code,
            message = response.message
        )
}