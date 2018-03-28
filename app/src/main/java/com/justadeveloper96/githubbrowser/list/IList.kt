package com.justadeveloper96.githubbrowser.list

import android.arch.lifecycle.LiveData
import com.justadeveloper96.githubbrowser.repo.Note
import com.justadeveloper96.helpers.arch.Resource

/**
 * Created by harshith on 06-03-2018.
 */
interface IList {

    interface View{
        fun showLoading(loading: Boolean)
        fun isFilterApplied():Boolean
        fun showEmptyNoteScreen()
        fun showEmptyResultScreen()
    }
    interface Actions{
        fun getList(): LiveData<Resource<List<Note>>>;
        fun applyFilter(name: String);
        //        fun getData():LiveData<List<Note>>
        fun getFilters():LiveData<String>
    }
}