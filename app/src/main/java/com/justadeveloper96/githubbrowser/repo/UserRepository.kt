package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.google.gson.Gson
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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.Executor
import javax.inject.Inject
import com.google.gson.reflect.TypeToken



/**
 * Created by harshith on 06-03-2018.
 */
class UserRepository @Inject constructor(val dao:UserDao, val executor: AppExecutors, val retrofit: Retrofit) {

    fun fetchUser(name:String):LiveData<Resource<List<User>>>{
        val users= MutableLiveData<Resource<List<User>>>()
        executor.networkIO().execute({
            if (dao.count("%$name%")<=0)
            {
                users.postValue(Resource.loading(null))
                try {
                    val response:Response<List<User>>?;
                    val result:List<User>?
                    val success:Boolean
                    if (name.isEmpty()) {
                        response = retrofit.create(GithubService::class.java).getUsers().execute();
                        result=response.body()!!
                        success=response.isSuccessful
                    }else
                    {
                        val data = retrofit.create(GithubService::class.java).getUsers(name).execute();
                        val listType = object : TypeToken<ArrayList<User>>() {}.type
                        result=Gson().fromJson(JSONObject(data.body()!!.string()).getJSONArray("items").toString(),listType);
                        success=data.isSuccessful
                    }

                    if (success) {
                        users.postValue(Resource.success(result))
                        result?.apply { dao.insertAll(this) }
                    } else {
                        users.postValue(Resource.error("Response Unsuccessful!", null))
                    }

                }catch(e:Exception)
                {
                    e.printStackTrace()
                    users.postValue(Resource.error(com.justadeveloper96.helpers.Utils.getNetworkErrorText(e),null))
                }
            }else{
                users.postValue(Resource.success(dao.find("%$name%")))
            }
        })
        return users;
    }
}