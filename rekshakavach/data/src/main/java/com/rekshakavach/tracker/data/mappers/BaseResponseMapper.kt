package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.data.entities.TokenReponse
import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.LoginEntity
import com.rekshakavach.tracker.domain.entity.TokenEntity

class BaseResponseMapper {

    fun map(response: Response<Array<String>>): BaseEntity = BaseEntity(
        message = response.message,
        status = response.status
    )

    fun mapLogin(response: Response<TokenReponse>): LoginEntity = LoginEntity(
        message = response.message,
        status = response.status,
        data = mapToken(response = response.data)
        )

    fun mapToken(response: TokenReponse?): TokenEntity = TokenEntity(
        token = response?.token
    )
}