package com.justadeveloper96.githubbrowser.repo

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by harshit on 28-03-2018.
 */
interface GithubService{

    @GET("/users")
    fun getUsers(@Query("per_page") count: Int=200):Call<List<User>>

    @GET("search/users")
    fun getUsers(@Query("q") name: String):Call<ResponseBody>
}