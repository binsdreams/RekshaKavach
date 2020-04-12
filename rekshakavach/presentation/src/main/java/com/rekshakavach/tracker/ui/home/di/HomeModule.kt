package com.rekshakavach.tracker.ui.home.di;

import com.rekshakavach.tracker.data.api.ProfileApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.ProfileRepoImpl
import com.rekshakavach.tracker.domain.repo.ProfileRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeModule {

    @Provides
    internal fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }

    @Provides
    internal fun provideProfileRepo(api: ProfileApi, cacheManager: CacheManager): ProfileRepo {
        return ProfileRepoImpl(api,cacheManager)
    }
}