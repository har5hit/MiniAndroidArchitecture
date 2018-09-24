package com.justadeveloper96.githubbrowser.list

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.justadeveloper96.githubbrowser.R
import com.justadeveloper96.githubbrowser.databinding.ListItemUserBinding
import com.justadeveloper96.githubbrowser.repo.db.User
import io.reactivex.subjects.PublishSubject

/**
 * Created by harshith on 06-03-2018.
 */
class ListAdapter: RecyclerView.Adapter<ListAdapter.UserViewHolder>() {

    val list:SortedList<User>

    val listener = PublishSubject.create<String>()

    init {
        list=SortedList(User::class.java,object : SortedListAdapterCallback<User>(this){
            override fun areItemsTheSame(item1: User?, item2: User?): Boolean = item1!!.id == item2!!.id

            override fun compare(o1: User?, o2: User?): Int = o2!!.id!!.compareTo(o1!!.id!!)

            override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean = oldItem!!.login == newItem!!.login

        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layout= DataBindingUtil.inflate<ListItemUserBinding>(LayoutInflater.from(parent.context), R.layout.list_item_user,parent,false)
        return UserViewHolder(layout)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item=list.get(position)
        holder.setData(item)
    }

    override fun onViewRecycled(holder: UserViewHolder) {

        //save memory by cleaning up non required bitmap data
        holder.clearImage()
        super.onViewRecycled(holder)
    }

    fun update(data:List<User>?)
    {
        if (data==null || data.isEmpty())
        {
            list.clear()
        }else
        {
            list.replaceAll(data)
        }
    }

    inner class UserViewHolder constructor(val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root), RequestListener<Drawable>, View.OnClickListener {
        override fun onClick(v: View?) {
            list.get(adapterPosition).htmlUrl?.let {
                listener.onNext(it)
            }
        }

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            binding.progressBar.visibility=View.GONE
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            binding.progressBar.visibility=View.GONE
            return false
        }

        fun setData(user: User)
        {
            binding.user=user
            binding.progressBar.visibility=View.VISIBLE
            Glide.with(binding.imageView.context).load(user.avatarUrl).apply(RequestOptions.circleCropTransform()).listener(this).into(binding.imageView)
        }

        fun clearImage()
        {
            Glide.with(binding.imageView.context).clear(binding.imageView)
        }
    }

    override fun getItemCount(): Int = list.size()

}