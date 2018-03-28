package com.justadeveloper96.githubbrowser.di

import android.app.ListActivity
import android.view.View
import com.justadeveloper96.githubbrowser.MainActivity
import com.justadeveloper96.helpers.di.AppModule
import com.justadeveloper96.helpers.di.RetrofitModule


/**
 * Created by harshit on 28-03-2018.
 */

import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = arrayOf(AppModule::class,ViewModelModule::class,RetrofitModule::class))
interface AppComponent {
    fun inject(app: ListActivity)
}