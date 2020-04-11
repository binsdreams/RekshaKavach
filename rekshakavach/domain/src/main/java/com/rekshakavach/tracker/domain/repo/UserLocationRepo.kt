package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface UserLocationRepo {

    suspend fun locationUpdate(scope: CoroutineScope,lat:Double,lng :Double,deviceId :String): ReceiveChannel<DataEntity<UserInfoEntity>>

    fun getUser() :UserInfoEntity
}