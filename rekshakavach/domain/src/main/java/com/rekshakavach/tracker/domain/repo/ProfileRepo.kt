package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface ProfileRepo {

    suspend fun getMyDetails(scope: CoroutineScope): ReceiveChannel<DataEntity<ProfileEntity>>

    fun getLogout(scope: CoroutineScope): ReceiveChannel<DataEntity<BaseEntity>>

    fun saveProfile(user :ProfileEntity)

    fun getProfile() :ProfileEntity

}