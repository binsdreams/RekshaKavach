package com.rekshakavach.tracker.ui.join.di;

import com.rekshakavach.tracker.data.api.PhoneLoginApi
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.data.repository.PhoneLoginRepoImpl
import com.rekshakavach.tracker.domain.repo.PhoneLoginRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class JoinPhoneEntryModule {

    @Provides
    internal fun provideGenerateOtpApi(retrofit: Retrofit): PhoneLoginApi {
        return retrofit.create(PhoneLoginApi::class.java)
    }

    @Provides
    internal fun provideGenerateOtpRepo( api: PhoneLoginApi,cacheManager: CacheManager): PhoneLoginRepo {
        return PhoneLoginRepoImpl(api,cacheManager)
    }
}