package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import com.rekshakavach.tracker.domain.entity.UserInfoEntity

class UserResponseMapper {

    fun map(response :UserDetailsResponse): UserInfoEntity =
        UserInfoEntity(
            user_id =response.user_id,
            phone =response.phone,
            name =response.name,
            dob =response.dob,
            registered_date =response.registered_date,
            sex =response.sex,
            address =response.address,
            covid_band =response.covid_band,
            score = response.score,
            hash_code = response.hash_code
        )
}