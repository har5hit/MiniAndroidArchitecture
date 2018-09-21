package com.justadeveloper96.githubbrowser.repo.network

import com.justadeveloper96.githubbrowser.repo.db.User
import com.justadeveloper96.githubbrowser.repo.db.UserList
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.network.NetworkHandler
import com.justadeveloper96.helpers.network.NetworkHandlerCustom
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class UserApi @Inject constructor(private val retrofit: Retrofit){

    private val client by lazy { retrofit.create(RetrofitApi::class.java) }

    public fun fetchUsers() =NetworkHandler(client.getUsers()).result

    public fun fetchUsers(keyword:String)= object : NetworkHandlerCustom<UserList,List<User>>(client.getUsers(keyword)){
        override fun map(data: UserList?): List<User> = data?.items?:emptyList()
    }.result

    private interface RetrofitApi{
        @GET("/users")
        fun getUsers(@Query("per_page") count: Int=200): Call<List<User>>

        @GET("search/users")
        fun getUsers(@Query("q") name: String): Call<UserList>
    }

}