package com.justadeveloper96.githubbrowser.di

import android.arch.lifecycle.ViewModel
import com.justadeveloper96.githubbrowser.list.ListViewModel
import com.justadeveloper96.helpers.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by harshit on 28-03-2018.
 */
@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(listViewModel: ListViewModel):ViewModel;
}