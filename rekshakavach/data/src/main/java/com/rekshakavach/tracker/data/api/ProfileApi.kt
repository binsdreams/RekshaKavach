package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {

    @GET("user/{userId}")
    fun getUserProfileAsync(@Path("userId") id: String): Deferred<UserDetailsResponse>

}