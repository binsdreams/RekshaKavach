package com.rekshakavach.tracker.di
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rekshakavach.tracker.BuildConfig
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.di.scopes.AppScoped
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * [NetworkModule] class is responsible for providing application level dependencies
 * Anotated with singleton annotation to tell dagger these dependencies also exists
 * when [AppComponent] alive and destroy these dependencies when [AppComponent] destroy
 */
@Module
class NetworkModule {

    @AppScoped
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseURl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @AppScoped
    fun provideOkHttpClient(cacheManager: CacheManager): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        var  clientBuilder = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor { chain: Interceptor.Chain ->
                val accessToken = cacheManager.getAccessKey()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ".plus(accessToken)).build()
                chain.proceed(request)
            }
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        return clientBuilder.build()
    }
    private fun getBaseURl():String{
        return BuildConfig.BASEURL
    }
}