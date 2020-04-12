package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.ProfileApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import com.rekshakavach.tracker.data.mappers.UserResponseMapper
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ErrorEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class ProfileRepoImpl(private val api: ProfileApi, private val cacheManager: CacheManager) : ProfileRepo {

    private var mapper = UserResponseMapper()
    override suspend fun  getUserProfileAsync(scope: CoroutineScope): ReceiveChannel<DataEntity<UserInfoEntity>>{

        return scope.produce {
            try {
                var userId = getUser().user_id?:""
                val response : UserDetailsResponse = api.getUserProfileAsync(userId).await()
                if(response.message?.isNullOrEmpty() == false || response.errors?.size > 0){
                    send(DataEntity.ERROR(ErrorEntity("Failure")))
                }else{
                    var user =mapper.map(response)
                    cacheManager.saveProfile(user)
                    cacheManager.saveAccess(user.user_id?:"")
                    send(DataEntity.SUCCESS(user))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getUser(): UserInfoEntity {
        return cacheManager.getProfile()
    }
}