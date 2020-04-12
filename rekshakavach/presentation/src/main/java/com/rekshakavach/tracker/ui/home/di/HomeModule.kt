package com.rekshakavach.tracker.ui.home.di;

import com.rekshakavach.tracker.data.api.UserCovidApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.UserCovidInfoRepoImpl
import com.rekshakavach.tracker.domain.repo.UserCovidInfoRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeModule {

    @Provides
    internal fun provideUserCovidApi(retrofit: Retrofit): UserCovidApi {
        return retrofit.create(UserCovidApi::class.java)
    }

    @Provides
    internal fun provideUserLocationRepo(api: UserCovidApi, cacheManager: CacheManager): UserCovidInfoRepo {
        return UserCovidInfoRepoImpl(api,cacheManager)
    }
}