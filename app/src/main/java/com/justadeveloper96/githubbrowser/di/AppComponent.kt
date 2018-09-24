package com.justadeveloper96.githubbrowser.di

import com.justadeveloper96.githubbrowser.AuthBasedActivity
import com.justadeveloper96.githubbrowser.SplashActivity
import com.justadeveloper96.githubbrowser.repo.RoomModule
import com.justadeveloper96.helpers.di.AppModule
import com.justadeveloper96.helpers.di.RetrofitModule


/**
 * Created by harshit on 28-03-2018.
 */

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = arrayOf(AppModule::class,ViewModelModule::class,RetrofitModule::class,RoomModule::class))
interface AppComponent {
    fun inject(activity: com.justadeveloper96.githubbrowser.list.ListActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: AuthBasedActivity)
}