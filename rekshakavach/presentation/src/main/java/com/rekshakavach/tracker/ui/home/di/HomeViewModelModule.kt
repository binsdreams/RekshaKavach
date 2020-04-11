package com.rekshakavach.tracker.ui.home.di;

import androidx.lifecycle.ViewModel
import com.rekshakavach.tracker.di.vm.ViewModelKey
import com.rekshakavach.tracker.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    /**
     * Binds the auth view model dependency with [ViewModelKey] to group similar [ViewModel]
     *
     * Under the hood it is providing MainViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(joinViewModel: HomeViewModel) : ViewModel
}