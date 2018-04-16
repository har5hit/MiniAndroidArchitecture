package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.di.AppExecutors
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import com.google.gson.reflect.TypeToken
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.helpers.di.SharedPrefs


/**
 * Created by harshith on 06-03-2018.
 */
class UserRepository @Inject constructor(val dao:UserDao, val executor: AppExecutors, val retrofit: Retrofit, val sharedPrefs: SharedPrefs) {

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

                        if(response.code()==401)
                        {
                            sharedPrefs.save(Constants.OAUTH_TOKEN,"")
                        }
                    }else
                    {
                        val data = retrofit.create(GithubService::class.java).getUsers(name).execute();
                        val listType = object : TypeToken<ArrayList<User>>() {}.type
                        result=Gson().fromJson(JSONObject(data.body()!!.string()).getJSONArray("items").toString(),listType);
                        success=data.isSuccessful
                        if(data.code()==401)
                        {
                            sharedPrefs.save(Constants.OAUTH_TOKEN,"")
                        }
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