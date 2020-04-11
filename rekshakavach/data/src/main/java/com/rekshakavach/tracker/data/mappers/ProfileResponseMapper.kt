package com.rekshakavach.tracker.data.mappers

import com.rekshakavach.tracker.data.entities.AddressResponse
import com.rekshakavach.tracker.data.entities.ProfileResponse
import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.domain.entity.AddressEntity
import com.rekshakavach.tracker.domain.entity.ProfileEntity

class ProfileResponseMapper {

    fun map(response: Response<ProfileResponse>): ProfileEntity = mapProfile(response.message,response.status,response.data)

    private fun mapProfile(message :String?,status :Int,response: ProfileResponse?): ProfileEntity = ProfileEntity(
        message = message,
        status = status,
        id= response?.id,
        user_key= response?.user_key,
        first_name= response?.first_name,
        last_name= response?.last_name,
        gender= response?.gender,
        dob= response?.dob,
        name= response?.name,
        email= response?.email,
        mobile= response?.mobile,
        city_id= response?.city_id,
        country_id= response?.country_id,
        subscription_enabled= response?.subscription_enabled,
        otp_validated= response?.otp_validated,
        email_verified_at= response?.email_verified_at,
        user_address =mapAddressList(response?.user_address)
    )

    fun mapAddress(response: AddressResponse?): AddressEntity = AddressEntity(
        id= response?.id,
        profile_id= response?.profile_id,
        address_type= response?.address_type,
        default= response?.default,
        display_name= response?.display_name,
        mobile= response?.mobile,
        building_name= response?.building_name,
        street_name= response?.street_name,
        landmark= response?.landmark,
        city_id= response?.city_id,
        city_name= response?.city_name,
        pin_code= response?.pin_code,
        country_id= response?.country_id,
        lat= response?.lat,
        long= response?.long
    )

    private fun mapAddressList(addressList: List<AddressResponse>?)
            : List<AddressEntity> = addressList?.map {
        mapAddress(it) } ?: emptyList()

}