package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegistrationApi {

    @POST("user")
    fun registerAsync(@Body reqBodyParams: Map<String, String>): Deferred<UserDetailsResponse>

}