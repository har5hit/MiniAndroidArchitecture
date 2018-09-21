package com.justadeveloper96.githubbrowser.list

import android.arch.lifecycle.*
import com.justadeveloper96.githubbrowser.repo.db.User
import com.justadeveloper96.githubbrowser.repo.UserRepository
import com.justadeveloper96.helpers.arch.Resource
import javax.inject.Inject

/**
 * Created by harshith on 06-03-2018.
 */
class ListViewModel @Inject constructor(val repo: UserRepository): ViewModel(), IList.Actions
{
    private val appliedFilters:MutableLiveData<String>

    init {
        appliedFilters=MutableLiveData()

        //initialize with blank data
        appliedFilters.value=""
    }


    override fun getList(): LiveData<Resource<List<User>>>{
        return Transformations.switchMap(appliedFilters,{input -> repo.fetchUser(name = input) })
    }

    override fun applyFilter(name: String) {
        appliedFilters.value=name
    }

    override fun getFilters() = appliedFilters
}