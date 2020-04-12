package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.UserCovidInfoResponse
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity

class UserCovidMapper {

    fun map(response :UserCovidInfoResponse): UserCovidInfoEntity =
        UserCovidInfoEntity(
            user_id =response.user_id,
            is_positive = response.is_positive,
            positive_declaration_date =response.positive_declaration_date,
            is_discharged = response.is_discharged,
            discharge_date =response.discharge_date
        )
}