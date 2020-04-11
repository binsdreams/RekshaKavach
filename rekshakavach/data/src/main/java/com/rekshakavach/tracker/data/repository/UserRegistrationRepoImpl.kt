package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.UserRegistrationApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.entities.UserDetailsResponse
import com.rekshakavach.tracker.data.mappers.UserResponseMapper
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ErrorEntity
import com.rekshakavach.tracker.domain.entity.UserInfoEntity
import com.rekshakavach.tracker.domain.repo.UserRegistrationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class UserRegistrationRepoImpl(private val api: UserRegistrationApi, private val cacheManager: CacheManager) : UserRegistrationRepo {

    private var mapper = UserResponseMapper()
    override suspend fun register(scope: CoroutineScope,user : UserInfoEntity): ReceiveChannel<DataEntity<UserInfoEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["name"] = user.name.toString()
                reqBodyParams["address"] = user.address?:""
                reqBodyParams["sex"] = user.sex?:"M"
                reqBodyParams["phone"] =user.phone.toString()
                reqBodyParams["registered_date"] = user.registered_date?:""
                reqBodyParams["dob"] = user.dob?:""
                val response : UserDetailsResponse = api.register(reqBodyParams).await()
                if(response.message?.isNullOrEmpty() == false || response.errors?.size > 0){
                    send(DataEntity.ERROR(ErrorEntity("Failure")))
                }else{
                    cacheManager.saveAccess("SAVES SUCCESS")
                    var user =mapper.map(response)
                    cacheManager.saveProfile(user)
                    send(DataEntity.SUCCESS(user))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getToken(): String {
        return cacheManager.getAccessKey()
    }
}