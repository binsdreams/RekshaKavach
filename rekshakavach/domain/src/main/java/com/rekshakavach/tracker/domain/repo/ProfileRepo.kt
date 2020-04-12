package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface ProfileRepo {

    suspend fun getUserProfileAsync(scope: CoroutineScope,userId :String): ReceiveChannel<DataEntity<UserInfoEntity>>

    suspend fun getMyProfile(scope: CoroutineScope): ReceiveChannel<DataEntity<UserInfoEntity>>

    fun getUser() :UserInfoEntity
}