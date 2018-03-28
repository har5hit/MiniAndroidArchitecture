package com.justadeveloper96.githubbrowser.list

import android.databinding.DataBindingUtil
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.daimajia.swipe.SwipeLayout
import com.justadeveloper96.githubbrowser.R
import com.justadeveloper96.githubbrowser.databinding.ListItemNoteBinding
import com.justadeveloper96.githubbrowser.repo.Note
import io.reactivex.subjects.PublishSubject

/**
 * Created by harshith on 06-03-2018.
 */
class ListAdapter: RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {

    val actionlistener= PublishSubject.create<Pair<Note,Int>>()!!

    val list:SortedList<Note>
    init {
        list=SortedList(Note::class.java,object : SortedListAdapterCallback<Note>(this){
            override fun areItemsTheSame(item1: Note?, item2: Note?): Boolean = item1!!.id == item2!!.id

            override fun compare(o1: Note?, o2: Note?): Int = o2!!.createdAt.compareTo(o1!!.createdAt)

            override fun areContentsTheSame(oldItem: Note?, newItem: Note?): Boolean = oldItem!!.updatedAt == newItem!!.updatedAt

        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layout= DataBindingUtil.inflate<ListItemNoteBinding>(LayoutInflater.from(parent.context), R.layout.list_item_note,parent,false)
        return NoteViewHolder(layout)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.setNote(list.get(position))
    }

    fun update(data:List<Note>?)
    {
        data?.apply {
            list.replaceAll(data)
        }
    }

    inner class NoteViewHolder constructor(val binding: ListItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun getItemCount(): Int = list.size()

}