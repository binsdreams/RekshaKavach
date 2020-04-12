package com.rekshakavach.tracker.di

import com.rekshakavach.tracker.RKTApplication
import com.rekshakavach.tracker.di.scopes.AppScoped
import com.rekshakavach.tracker.di.vm.ViewModelFactoryModule
import com.rekshakavach.tracker.ui.home.di.HomeActivityModule
import com.rekshakavach.tracker.ui.join.di.PhoneEntryFragmentModule
import com.rekshakavach.tracker.ui.mark.di.MarkCovidActivityModule
import com.rekshakavach.tracker.ui.scan.di.ScannerActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component is a graph. We build a component. Component will provide injected instances by using modules.
 * Extends appcomponent with [AndroidInjector] to avoid old way of injection application
 *
 * <code>
 *     fun inject(application: BaseApplication)
 * </code>
 *
 * AppComponent is act as a server whereas, [Application] act as a client.
 * Dagger interaction is like client-server interaction
 *
 * Anotated with [Singleton] Scope to tell dagger to keep it in the memory while application exists
 * and destroy it when application destroy
 */
@AppScoped
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    MainModule::class,
    ViewModelFactoryModule::class,
    PhoneEntryFragmentModule::class,
    HomeActivityModule::class,
    MarkCovidActivityModule::class,
    ScannerActivityModule::class
])
interface AppComponent : AndroidInjector<RKTApplication> {

    @Component.Builder
    interface Builder {
        /**
         * [BindsInstance] annotation is used for, if you want to bind particular object or instance
         * of an object through the time of component construction
         */
        @BindsInstance
        fun application(application: RKTApplication) : Builder

        fun mainModule(clipyModule: MainModule) : Builder

        fun build() : AppComponent
    }

}