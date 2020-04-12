package com.rekshakavach.tracker

import com.rekshakavach.tracker.di.DaggerAppComponent
import com.rekshakavach.tracker.di.MainModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RKTApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder()
            .application(this)
            .mainModule(MainModule(this))
            .build()
    }
}