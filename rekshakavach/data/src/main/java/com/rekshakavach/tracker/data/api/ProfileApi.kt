package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.ProfileResponse
import com.rekshakavach.tracker.data.entities.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ProfileApi {

    @GET("users/details")
    fun getMyDetailsAsyc(): Deferred<Response<ProfileResponse>>

    @GET("users/logout")
    fun getLogoutSync(): Deferred<Response<Array<String>>>

}