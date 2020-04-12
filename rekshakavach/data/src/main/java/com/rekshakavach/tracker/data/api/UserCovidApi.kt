package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.BaseResponse
import com.rekshakavach.tracker.data.entities.UserCovidInfoResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserCovidApi {

    @POST("user/detected/{userId}")
    fun detectedCovidAsync(@Path("userId") id: String?,@Body reqBodyParams :Map<String, String>): Deferred<UserCovidInfoResponse>

}