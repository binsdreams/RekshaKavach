package com.rekshakavach.tracker.data.api

import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.data.entities.TokenReponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface PhoneLoginApi {

    @POST("login/otp/generate")
    fun generateOtpAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response<Array<String>>>

    @POST("login/otp/validate")
    fun validateOtpAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response<TokenReponse>>

    @POST("login/otp/resend")
    fun resendOtpAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response<Array<String>>>

    @POST("users/update")
    fun saveProfileAsync(@Body reqBodyParams :Map<String, String>): Deferred<Response<Array<String>>>

//    @POST("checkout-service/payfort/users/{userId}/purchase")
////    fun purchase(
////        @Path("userId") userId: String?,
////        @Body param: HashMap<String?, String?>?
////    ): Observable<ResponseWrapper<PurchaseVo?>?>?


}