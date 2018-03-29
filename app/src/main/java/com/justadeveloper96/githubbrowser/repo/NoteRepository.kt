package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.justadeveloper96.githubbrowser.helpers.Utils
import com.justadeveloper96.helpers.Utils.context
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.di.AppExecutors
import com.justadeveloper96.helpers.di.RetrofitModule
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by harshith on 06-03-2018.
 */
class NoteRepository @Inject constructor(val dao:NoteDao,val executor: AppExecutors,val retrofit: Retrofit) {

    fun fetchUser(name:String):LiveData<Resource<List<Note>>>{
        val users= MutableLiveData<Resource<List<Note>>>()
        executor.networkIO().execute({
            if (dao.count("%$name%")<=0)
            {
                users.postValue(Resource.loading(null))
                try {
                    val response = retrofit.create(GithubService::class.java).getUsers().execute();
                    response?.apply {
                        if (isSuccessful) {
                            users.postValue(Resource.success(response.body()))
                            response.body()?.apply { dao.insertAll(this) }
                        } else {
                            users.postValue(Resource.error("Response Unsuccessful!", null))
                        }
                    }
                }catch(e:Exception)
                {
                    users.postValue(Resource.error(com.justadeveloper96.helpers.Utils.getNetworkErrorText(e),null))
                }
            }else{
                users.postValue(Resource.success(dao.find("%$name%")))
            }
        })
        return users;
    }

    fun getAllLive(): LiveData<List<Note>> {
        return dao.all
    }
}