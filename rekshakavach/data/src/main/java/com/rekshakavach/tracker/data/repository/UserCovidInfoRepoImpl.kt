package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.UserCovidApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.domain.entity.*
import com.rekshakavach.tracker.domain.repo.UserCovidInfoRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class UserCovidInfoRepoImpl(private val api: UserCovidApi, private val cacheManager: CacheManager) : UserCovidInfoRepo {

    override suspend fun updateCovidAsync(scope: CoroutineScope,covid : UserCovidInfoEntity): ReceiveChannel<DataEntity<BaseEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["user_id"] = covid.user_id?:""
                reqBodyParams["is_positive"] = "".plus(covid.is_positive?:false)
                reqBodyParams["positive_declaration_date"] = covid.positive_declaration_date?:""
                reqBodyParams["is_discharged"] = "".plus(covid.is_discharged?:false)
                reqBodyParams["discharge_date"] =covid.discharge_date?:""
                api.detectedCovidAsync(covid.user_id,reqBodyParams).await()
                send(DataEntity.ERROR(ErrorEntity("Succcess")))
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getUser(): UserInfoEntity {
        return cacheManager.getProfile()
    }

}