package com.justadeveloper96.githubbrowser

import android.arch.lifecycle.MutableLiveData
import com.justadeveloper96.helpers.arch.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerStatusEvent @Inject constructor(){

    val code by lazy { MutableLiveData<Status>() }

}