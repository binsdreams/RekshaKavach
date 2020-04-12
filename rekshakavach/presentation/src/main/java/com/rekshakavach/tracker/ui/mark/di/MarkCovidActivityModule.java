package com.rekshakavach.tracker.ui.mark.di;

import com.rekshakavach.tracker.di.scopes.FragmentScoped;
import com.rekshakavach.tracker.ui.mark.MarkCovidActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This Class {@linkplain MarkCovidActivityModule} is responsible for for android injection
 * for the activity with in the application to avoid the seprate injection in each activity
 *
 * {@linkplain dagger.android.AndroidInjection}
 *
 * only so it is the concept of sub-modules
 *
 */
@Module
public abstract class MarkCovidActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector(
            modules = { MarkCovidModule.class, MarkCovidViewModelModule.class}
    )
    abstract MarkCovidActivity contributeMarkCovidActivity();

}
