package com.rekshakavach.tracker.domain.repo

import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel

interface UserCovidInfoRepo {

    suspend fun updateCovidAsync(scope: CoroutineScope,covid :UserCovidInfoEntity): ReceiveChannel<DataEntity<BaseEntity>>

    fun getUser() :UserInfoEntity
}