package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.Gson
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.di.AppExecutors
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import com.justadeveloper96.githubbrowser.ServerStatusEvent
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.githubbrowser.repo.db.User
import com.justadeveloper96.githubbrowser.repo.db.UserDao
import com.justadeveloper96.githubbrowser.repo.network.UserApi
import com.justadeveloper96.helpers.arch.Status
import com.justadeveloper96.helpers.di.SharedPrefs
import kotlin.math.log


/**
 * Created by harshith on 06-03-2018.
 */
class UserRepository @Inject constructor(private val userDao: UserDao, private val executor: AppExecutors, private val userApi: UserApi, private val sharedPrefs: SharedPrefs, private val serverStatus: ServerStatusEvent) {

    fun fetchUser(name:String):LiveData<Resource<List<User>>>{
        val users= MutableLiveData<Resource<List<User>>>()
        executor.networkIO().execute {
            if(userDao.count("%$name%")<=0)
            {
                users.postValue(Resource.loading(null))
                val response= if (name.isBlank()) userApi.fetchUsers() else userApi.fetchUsers(name)
                if (401==response.code)
                {
                    logout()
                }
                if (response.status==Status.SUCCESS)
                {
                    response.data?.let { userDao.insertAll(it) }
                }
                users.postValue(response)
            }else{
                users.postValue(Resource.success(userDao.find("%$name%")))
            }
        }
        return users;
    }

    val TAG=this::class.java.simpleName

    fun logout(){
        sharedPrefs.save(Constants.OAUTH_TOKEN,"")
        Log.d(TAG,"logging out")
        serverStatus.code.postValue(Status.LOGOUT)
    }
}