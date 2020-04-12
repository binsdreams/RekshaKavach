package com.rekshakavach.tracker.ui.mark.di;

import androidx.lifecycle.ViewModel
import com.rekshakavach.tracker.di.vm.ViewModelKey
import com.rekshakavach.tracker.ui.mark.MArkCovidViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MarkCovidViewModelModule {

    /**
     * Binds the auth view model dependency with [ViewModelKey] to group similar [ViewModel]
     *
     * Under the hood it is providing MainViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(MArkCovidViewModel::class)
    abstract fun bindMArkCovidViewModel(joinViewModel: MArkCovidViewModel) : ViewModel
}