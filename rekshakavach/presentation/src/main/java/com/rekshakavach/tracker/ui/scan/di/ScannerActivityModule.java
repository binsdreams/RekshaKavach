package com.rekshakavach.tracker.ui.scan.di;

import com.rekshakavach.tracker.di.scopes.FragmentScoped;
import com.rekshakavach.tracker.ui.home.di.HomeModule;
import com.rekshakavach.tracker.ui.home.di.HomeViewModelModule;
import com.rekshakavach.tracker.ui.scan.ScannedActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This Class {@linkplain ScannerActivityModule} is responsible for for android injection
 * for the activity with in the application to avoid the seprate injection in each activity
 *
 * {@linkplain dagger.android.AndroidInjection}
 *
 * only so it is the concept of sub-modules
 *
 */
@Module
public abstract class ScannerActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector(
            modules = { HomeViewModelModule.class, HomeModule.class}
    )
    abstract ScannedActivity contributeScannedActivity();

}
