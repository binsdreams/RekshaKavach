package com.rekshakavach.tracker.di
import android.content.Context
import com.rekshakavach.tracker.RKTApplication
import com.rekshakavach.tracker.data.cache.CacheManager
import com.rekshakavach.tracker.di.scopes.AppScoped
import dagger.Module
import dagger.Provides

/**
 * [MainModule] class is responsible for providing application level dependencies
 * Anotated with singleton annotation to tell dagger these dependencies also exists
 * when [AppComponent] alive and destroy these dependencies when [AppComponent] destroy
 */
@Module
class MainModule (private val application: RKTApplication) {

    @AppScoped
    @Provides
    fun provideCacheManager(): CacheManager {
        return CacheManager(application.getSharedPreferences("ClipyCachePreference",Context.MODE_PRIVATE))
    }
}