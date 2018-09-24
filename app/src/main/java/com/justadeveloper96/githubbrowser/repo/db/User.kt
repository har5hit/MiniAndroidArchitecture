package com.justadeveloper96.githubbrowser.repo.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.justadeveloper96.githubbrowser.R.string.starred


/**
 * Created by harshith on 06-03-2018.
 */
@Entity
data class User(@NonNull @PrimaryKey val id: Int,
                val login:String?,
                @SerializedName("avatar_url") @Expose val avatarUrl: String?,
                @SerializedName("html_url") @Expose val htmlUrl: String?)

@Entity
data class UserList(val items:List<User>)