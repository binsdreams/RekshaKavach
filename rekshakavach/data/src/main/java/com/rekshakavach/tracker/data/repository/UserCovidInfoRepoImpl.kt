package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.UserCovidApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.mappers.UserCovidMapper
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ErrorEntity
import com.rekshakavach.tracker.domain.entity.UserCovidInfoEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.UserCovidInfoRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class UserCovidInfoRepoImpl(private val api: UserCovidApi, private val cacheManager: CacheManager) : UserCovidInfoRepo {
    var mapper =UserCovidMapper()
    override suspend fun updateCovidAsync(scope: CoroutineScope,covid : UserCovidInfoEntity): ReceiveChannel<DataEntity<UserCovidInfoEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["user_id"] = covid.user_id?:""
                reqBodyParams["is_positive"] = "".plus(covid.is_positive?:false)
                reqBodyParams["positive_declaration_date"] = covid.positive_declaration_date?:""
                reqBodyParams["is_discharged"] = "".plus(covid.is_discharged?:false)
                reqBodyParams["discharge_date"] =covid.discharge_date?:""
                var response = api.detectedCovidAsync(covid.user_id,reqBodyParams).await()
                send(DataEntity.SUCCESS(mapper.map(response)))
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getUser(): UserInfoEntity {
        return cacheManager.getProfile()
    }

}