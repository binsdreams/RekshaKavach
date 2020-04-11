package com.rekshakavach.tracker.ui.join.di;

import com.rekshakavach.tracker.di.scopes.FragmentScoped;
import com.rekshakavach.tracker.ui.join.JoinPhoneActivity;
import com.rekshakavach.tracker.ui.join.LoginFragment;
import com.rekshakavach.tracker.ui.join.RegisterFragment;
import com.rekshakavach.tracker.ui.join.UserDetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This Class {@linkplain PhoneEntryFragmentModule} is responsible for for android injection
 * for the activity with in the application to avoid the seprate injection in each activity
 *
 * {@linkplain dagger.android.AndroidInjection}
 *
 * only so it is the concept of sub-modules
 *
 */
@Module
public abstract class PhoneEntryFragmentModule {

    /**
     * Automatically create sub-component
     * @return
     */
    @FragmentScoped
    @ContributesAndroidInjector(
            modules = { JoinPhoneViewModelModule.class, JoinPhoneEntryModule.class}
    )
    abstract LoginFragment contributePhoneEntryFragment();

    @FragmentScoped
    @ContributesAndroidInjector(
            modules = { JoinPhoneViewModelModule.class, JoinPhoneEntryModule.class}
    )
    abstract RegisterFragment contributePhoneVerifyFragment();

    @FragmentScoped
    @ContributesAndroidInjector(
            modules = { JoinPhoneViewModelModule.class, JoinPhoneEntryModule.class}
    )
    abstract UserDetailsFragment contributeUserDetailsFragment();

}
