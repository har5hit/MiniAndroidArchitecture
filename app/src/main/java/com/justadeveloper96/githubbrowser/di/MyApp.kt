package com.justadeveloper96.githubbrowser.di

import android.app.Application
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.githubbrowser.repo.RoomModule
import com.justadeveloper96.helpers.di.AppModule
import com.justadeveloper96.helpers.di.RetrofitModule
import com.justadeveloper96.helpers.di.SharedPrefs
import com.readystatesoftware.chuck.Chuck
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor

/**
 * Created by harshit on 28-03-2018.
 */
class MyApp:Application()
{
    public lateinit var injector: AppComponent
    override fun onCreate() {
        super.onCreate()
        injector=DaggerAppComponent.builder().appModule(AppModule(this))
                .roomModule(RoomModule())
                .retrofitModule(RetrofitModule("https://api.github.com/",null,null)).build()
    }

    fun reinitWithToken(token:String){

        val httpInterceptor=Interceptor({chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .header("Authorization", "token " + token)
            chain.proceed(requestBuilder.build())
        })

        val chuck=ChuckInterceptor(this)

        injector=DaggerAppComponent.builder().appModule(AppModule(this))
                .roomModule(RoomModule())
                .retrofitModule(RetrofitModule("https://api.github.com/",
                        listOf(httpInterceptor,chuck), null))
                .build()
    }
}