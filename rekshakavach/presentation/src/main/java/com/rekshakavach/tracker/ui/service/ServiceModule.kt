package com.rekshakavach.tracker.ui.service

import com.rekshakavach.tracker.data.api.UserLocationApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.UserLocationRepoImpl
import com.rekshakavach.tracker.domain.repo.UserLocationRepo
import retrofit2.Retrofit

class ServiceModule {

    fun provideUserUserLocationApi(retrofit: Retrofit): UserLocationApi {
        return retrofit.create(UserLocationApi::class.java)
    }

    fun provideUserLocationRepo(api: UserLocationApi, cacheManager: CacheManager): UserLocationRepo {
        return UserLocationRepoImpl(api,cacheManager)
    }

}