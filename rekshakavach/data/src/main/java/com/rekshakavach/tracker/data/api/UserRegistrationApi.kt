package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import kotlinx.coroutines.Deferred
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegistrationApi {

    @POST("user")
    fun register(@Body reqBodyParams :Map<String, String>): Deferred<UserDetailsResponse>

//    @POST("login/otp/validate")
//    fun validateOtpAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response>
//
//    @POST("login/otp/resend")
//    fun resendOtpAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response>
//
//    @POST("users/update")
//    fun saveProfileAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response>

//    @POST("checkout-service/payfort/users/{userId}/purchase")
////    fun purchase(
////        @Path("userId") userId: String?,
////        @Body param: HashMap<String?, String?>?
////    ): Observable<ResponseWrapper<PurchaseVo?>?>?


}