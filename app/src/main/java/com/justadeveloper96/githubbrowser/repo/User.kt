package com.justadeveloper96.githubbrowser.repo

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
class User(){
    @SerializedName("login")
    @Expose
    public var login: String? = null

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    public var id: Int? = null
    @SerializedName("avatar_url")
    @Expose
    public var avatarUrl: String? = null
    @SerializedName("html_url")
    @Expose
    public var htmlUrl: String? = null

    override fun toString(): String {
        return "User(login=$login, id=$id, avatarUrl=$avatarUrl, htmlUrl=$htmlUrl)"
    }


}