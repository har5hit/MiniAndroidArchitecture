package com.justadeveloper96.githubbrowser.model

import com.justadeveloper96.githubbrowser.repo.UserRepository
import javax.inject.Inject


class UserModel @Inject constructor(val userRepo: UserRepository){

    fun getUser(username:String)=userRepo.fetchUser(username)

    fun logout()=userRepo.logout()
}