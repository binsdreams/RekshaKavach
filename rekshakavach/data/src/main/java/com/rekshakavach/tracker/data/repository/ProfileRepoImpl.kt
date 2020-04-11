package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.ProfileApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.entities.ProfileResponse
import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.data.mappers.BaseResponseMapper
import com.rekshakavach.tracker.data.mappers.ProfileResponseMapper
import com.rekshakavach.tracker.domain.entity.*
import com.rekshakavach.tracker.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class ProfileRepoImpl(private val api: ProfileApi, private val cacheManager: CacheManager) : ProfileRepo {
    private val mapper = ProfileResponseMapper()
    private val baseMapper = BaseResponseMapper()


    override suspend fun getMyDetails(scope: CoroutineScope): ReceiveChannel<DataEntity<ProfileEntity>> {
        return scope.produce {
            try {
                val response : Response<ProfileResponse> = api.getMyDetailsAsyc().await()
                if(response.status == 200){
                    var profileEntity =mapper.map(response)
                    cacheManager.saveProfile(profileEntity)
                    send(DataEntity.SUCCESS(profileEntity))
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getLogout(scope: CoroutineScope): ReceiveChannel<DataEntity<BaseEntity>> {
        return scope.produce {
            try {
                val response :Response<Array<String>> = api.getLogoutSync().await()
                if(response.status == 200){
                    cacheManager.clear()
                    send(DataEntity.SUCCESS(baseMapper.map(response)))
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun saveProfile(profileEntity: ProfileEntity) {
        cacheManager.saveProfile(profileEntity)
    }

    override fun getProfile(): ProfileEntity {
        return cacheManager.getProfile()
    }
}