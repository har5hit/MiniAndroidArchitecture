package com.justadeveloper96.githubbrowser.di

import android.app.Application

/**
 * Created by harshit on 28-03-2018.
 */
class MyApp:Application()
{
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

    }
}