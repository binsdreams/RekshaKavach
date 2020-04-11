package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface UserRegistrationRepo {

    suspend fun register(scope: CoroutineScope,user : UserInfoEntity): ReceiveChannel<DataEntity<UserInfoEntity>>

    fun getToken() :String
}