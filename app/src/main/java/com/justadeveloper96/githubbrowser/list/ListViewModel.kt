package com.justadeveloper96.githubbrowser.list

import android.app.Application
import android.arch.lifecycle.*
import com.justadeveloper96.githubbrowser.list.IList
import com.justadeveloper96.githubbrowser.repo.Note
import com.justadeveloper96.githubbrowser.repo.NoteRepository
import com.justadeveloper96.helpers.arch.Resource
import javax.inject.Inject

/**
 * Created by harshith on 06-03-2018.
 */
class ListViewModel @Inject constructor(val repo: NoteRepository): ViewModel(), IList.Actions
{
   // override fun getData(): LiveData<List<Note>> = filterer
   // private val filterer=MediatorLiveData<List<Note>>()

   // private val dbSource:LiveData<List<Note>>

    private val appliedFilters:MutableLiveData<String>

    init {
//        dbSource=getList()
//        filterer.addSource(dbSource,{
//            filterer.value=it
//        })
        appliedFilters=MutableLiveData()
        appliedFilters.value=""
    }


    override fun getList(): LiveData<Resource<List<Note>>>{
        return Transformations.switchMap(appliedFilters,{input -> repo.fetchUser(name = input) })
    }

    override fun applyFilter(name: String) {
        appliedFilters.value=name
//        filterer.removeSource(dbSource)
//        filterer.addSource(dbSource,{
//            var res=it
//            if (filterLiked)
//            {
//                res=res?.filter { it.liked }
//            }
//            if (filterStarred)
//            {
//                res=res?.filter { it.starred }
//            }
        /*   filterer.value=res
       })*/
    }

    override fun getFilters() = appliedFilters
}