package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.LoginEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface PhoneLoginRepo {

    suspend fun generateOtp(scope: CoroutineScope,countryCode :String,mobile :String): ReceiveChannel<DataEntity<BaseEntity>>

    suspend fun validateOtp(scope: CoroutineScope,countryCode :String,mobile :String,otp :String): ReceiveChannel<DataEntity<LoginEntity>>

    suspend fun resendOtp(scope: CoroutineScope,countryCode :String,mobile :String): ReceiveChannel<DataEntity<BaseEntity>>

    suspend fun saveUserProfile(scope: CoroutineScope,email :String,fullname :String): ReceiveChannel<DataEntity<BaseEntity>>

    fun getToken() :String
}