package com.justadeveloper96.githubbrowser.repo

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by harshit on 28-03-2018.
 */
interface GithubService{

    @GET("/users")
    fun getUsers():Call<List<Note>>
}