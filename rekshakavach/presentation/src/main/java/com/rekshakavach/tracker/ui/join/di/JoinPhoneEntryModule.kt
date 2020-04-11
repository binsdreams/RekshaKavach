package com.rekshakavach.tracker.ui.join.di;

import com.rekshakavach.tracker.data.api.UserRegistrationApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.UserRegistrationRepoImpl
import com.rekshakavach.tracker.domain.repo.UserRegistrationRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class JoinPhoneEntryModule {

    @Provides
    internal fun provideUserRegistrationApi(retrofit: Retrofit): UserRegistrationApi {
        return retrofit.create(UserRegistrationApi::class.java)
    }

    @Provides
    internal fun provideUserRegistrationRepo(api: UserRegistrationApi, cacheManager: CacheManager): UserRegistrationRepo {
        return UserRegistrationRepoImpl(api,cacheManager)
    }
}