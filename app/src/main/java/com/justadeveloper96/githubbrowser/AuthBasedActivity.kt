package com.justadeveloper96.githubbrowser

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.justadeveloper96.githubbrowser.di.DIHolder
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.arch.Status
import javax.inject.Inject

open class AuthBasedActivity: AppCompatActivity() {

    @Inject
    lateinit var serverStatusEvent: ServerStatusEvent

    val TAG=this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DIHolder.injector.inject(this)
        serverStatusEvent.code.observe(this, Observer {
            Log.d(TAG,"code is ${it.toString()}")
            if (it == Status.LOGOUT)
            {
                startActivity(
                        Intent(this,SplashActivity::class.java).apply
                        { flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }
                )
                finish()
            }
        })
    }
}