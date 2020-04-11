package com.rekshakavach.tracker.ui.home.di;

import com.rekshakavach.tracker.data.api.UserLocationApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.UserLocationRepoImpl
import com.rekshakavach.tracker.domain.repo.UserLocationRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeModule {

    @Provides
    internal fun provideUserUserLocationApi(retrofit: Retrofit): UserLocationApi {
        return retrofit.create(UserLocationApi::class.java)
    }

    @Provides
    internal fun provideUserRegistrationRepo(api: UserLocationApi, cacheManager: CacheManager): UserLocationRepo {
        return UserLocationRepoImpl(api,cacheManager)
    }
}