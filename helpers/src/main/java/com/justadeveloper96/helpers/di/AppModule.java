package com.justadeveloper96.helpers.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sankalp on 21/2/17.
 */
@Module
public class AppModule {
    private Application mApp;

    public AppModule(Application application)
    {
        this.mApp=application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public Application provideAppInstance() {
        return mApp;
    }

    @Provides
    @Singleton
    SharedPrefs provideSharedPrefs(Context context) {
        return new SharedPrefs(context);
    }

    @Provides
    @Singleton
    AppExecutors provideExecutors() {
        return new AppExecutors();
    }
}
