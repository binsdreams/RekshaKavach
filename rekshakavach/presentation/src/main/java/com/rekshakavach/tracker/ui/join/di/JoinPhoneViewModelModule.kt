package com.rekshakavach.tracker.ui.join.di;

import androidx.lifecycle.ViewModel
import com.rekshakavach.tracker.di.vm.ViewModelKey
import com.rekshakavach.tracker.ui.join.JoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class JoinPhoneViewModelModule {

    /**
     * Binds the auth view model dependency with [ViewModelKey] to group similar [ViewModel]
     *
     * Under the hood it is providing MainViewModel
     */
    @Binds
    @IntoMap
    @ViewModelKey(JoinViewModel::class)
    abstract fun bindJoinViewModel(joinViewModel: JoinViewModel) : ViewModel
}