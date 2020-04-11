package com.rekshakavach.tracker.data.repository

import com.rekshakavach.tracker.data.api.PhoneLoginApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.entities.Response
import com.rekshakavach.tracker.data.entities.TokenReponse
import com.rekshakavach.tracker.data.mappers.BaseResponseMapper
import com.rekshakavach.tracker.domain.entity.BaseEntity
import com.rekshakavach.tracker.domain.entity.DataEntity
import com.rekshakavach.tracker.domain.entity.ErrorEntity
import com.rekshakavach.tracker.domain.entity.LoginEntity
import com.rekshakavach.tracker.domain.repo.PhoneLoginRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class PhoneLoginRepoImpl(private val api: PhoneLoginApi,private val cacheManager: CacheManager) : PhoneLoginRepo {
    private val mapper = BaseResponseMapper()

    override suspend fun generateOtp(scope: CoroutineScope,countryCode :String,mobile :String): ReceiveChannel<DataEntity<BaseEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["country_code"] =countryCode
                reqBodyParams["mobile"] = mobile
                val response :Response<Array<String>> = api.generateOtpAsync(reqBodyParams).await()
                if(response.status == 200){
                    send(DataEntity.SUCCESS(mapper.map(response)))
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override fun getToken(): String {
        return cacheManager.getAccessKey()
    }


    override suspend fun validateOtp(scope: CoroutineScope,countryCode :String,mobile :String,otp :String): ReceiveChannel<DataEntity<LoginEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["country_code"] =countryCode
                reqBodyParams["mobile"] = mobile
                reqBodyParams["otp"] = otp
                val response :Response<TokenReponse> = api.validateOtpAsync(reqBodyParams).await()
                if(response.status == 200){
                    if(response?.data?.token == null){
                        send(DataEntity.ERROR(ErrorEntity(response.message)))
                    }else{
                        cacheManager.saveAccess(response!!.data!!.token!!)
                        send(DataEntity.SUCCESS(mapper.mapLogin(response)))
                    }
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override suspend fun saveUserProfile(scope: CoroutineScope, email: String, fullname: String): ReceiveChannel<DataEntity<BaseEntity>> {
        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                var names = fullname.split(" ")
                if(names.size > 0) {
                    reqBodyParams["first_name"] = names[0]
                }
                if(names.size > 1) {
                    reqBodyParams["last_name"] = names[1]
                }
                reqBodyParams["name"] =fullname
                reqBodyParams["email"] = email
                val response :Response<Array<String>> = api.saveProfileAsync(reqBodyParams).await()
                if(response.status == 200){
                    send(DataEntity.SUCCESS(mapper.map(response)))
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }

    override suspend fun resendOtp(scope: CoroutineScope,countryCode :String,mobile :String): ReceiveChannel<DataEntity<BaseEntity>>{

        return scope.produce {
            try {
                var reqBodyParams = mutableMapOf<String,String>()
                reqBodyParams["country_code"] =countryCode
                reqBodyParams["mobile"] = mobile
                val response :Response<Array<String>> = api.generateOtpAsync(reqBodyParams).await()
                if(response.status == 200){
                    send(DataEntity.SUCCESS(mapper.map(response)))
                }else{
                    send(DataEntity.ERROR(ErrorEntity(response.message)))
                }
            } catch (e: Exception) {
                send(DataEntity.ERROR(ErrorEntity(e.message)))
            }
        }
    }



}