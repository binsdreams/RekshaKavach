package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.UserLocationApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.mappers.UserResponseMapper
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ErrorEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.UserLocationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class UserLocationRepoImpl(private val api: UserLocationApi, private val cacheManager: CacheManager) : UserLocationRepo {

    private var mapper = UserResponseMapper()
    override suspend fun locationUpdate(scope: CoroutineScope,lat:Double,lng :Double,deviceId :String): ReceiveChannel<DataEntity<UserInfoEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                var userProfile = getUser()
                reqBodyParams["user_id"] = deviceId
                reqBodyParams["device_id"] = deviceId?:""
                reqBodyParams["lat"] = lat.toString()
                reqBodyParams["lng"] = lng.toString()
                reqBodyParams["timestamp"] = System.currentTimeMillis().toString()
                api.locationUpdate(reqBodyParams).await()
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