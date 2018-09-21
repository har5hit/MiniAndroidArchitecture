package com.justadeveloper96.githubbrowser.list

import android.arch.lifecycle.LiveData
import com.justadeveloper96.githubbrowser.repo.db.User
import com.justadeveloper96.helpers.arch.Resource

/**
 * Created by harshith on 06-03-2018.
 */
interface IList {

    interface View{
        fun showLoading(loading: Boolean)
        fun isFilterApplied():Boolean
        fun showEmptyUserScreen()
        fun showErrorScreen(error: String)
    }
    interface Actions{
        fun getList(): LiveData<Resource<List<User>>>;
        fun applyFilter(name: String);
        fun getFilters():LiveData<String>
    }
}