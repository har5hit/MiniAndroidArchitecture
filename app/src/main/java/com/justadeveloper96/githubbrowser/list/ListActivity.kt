package com.justadeveloper96.githubbrowser.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.content_notes_list.*
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import com.justadeveloper96.githubbrowser.R
import com.justadeveloper96.githubbrowser.R.id.list_toolbar
import com.justadeveloper96.githubbrowser.R.id.tv_empty_filter_list
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.githubbrowser.helpers.Font
import com.justadeveloper96.githubbrowser.helpers.SharedPrefs
import com.justadeveloper96.githubbrowser.list.IList
import com.justadeveloper96.githubbrowser.repo.Note
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.arch.Status
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * Created by harshith on 06-03-2018.
 */

class ListActivity @Inject constructor(val sharedPrefs: SharedPrefs) : AppCompatActivity(), IList.View {



    override fun showLoading(loading: Boolean) {
        tv_empty_filter_list.visibility = if (loading) View.VISIBLE else View.GONE
    }

    var userName:String?=null
    override fun isFilterApplied(): Boolean = !userName.isNullOrEmpty();

    private val keplarFont by lazy {  Font.getFont(baseContext, Constants.keplarStdFont) }
    private val adapter by lazy { ListAdapter() }
    private val viewmodel by lazy { ViewModelProviders.of(this).get(ListViewModel::class.java) as IList.Actions }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Toast.makeText(applicationContext,sharedPrefs.getString("oauth_token"),Toast.LENGTH_LONG).show()
        initViews()

        //get filters from viewmodel if there was any before configuration change
        viewmodel.getFilters().observe(this,Observer<String>{t->
            t?.let {
                userName=t
            }
        })

        /*viewmodel.getData().observe(this, Observer<List<Note>> { t ->
            t?.let {
                //remove all empty texts
                tv_empty_filter_list.visibility = View.GONE
                tv_empty_list.visibility = View.GONE

                //update the sortedlist in adapter
                updateList(t)

                //if list is empty check if there are no notes
                if(t.isEmpty()) {

                    //if its empty because of results
                    if(isFilterApplied()) {
                        showEmptyResultScreen()
                    }else{
                        showEmptyNoteScreen()
                    }
                }
            }

        })*/

        viewmodel.getList().observe(this, Observer<Resource<List<Note>>> { t ->
            t?.let {
                //remove all empty texts
                reset()
                when (t.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }

                    Status.ERROR -> {
                        //if list is empty check if there are no notes
                        if(isFilterApplied()) {
                            showEmptyResultScreen()
                        }else{
                            showEmptyNoteScreen()
                        }
                    }

                    Status.SUCCESS -> {
                        adapter.update(t.data)
                    }
                }

            }

        })
    }

    private fun reset() {
        tv_empty_filter_list.visibility = View.GONE
        tv_empty_list.visibility = View.GONE
        showLoading(false)
    }

    private fun initViews() {
        //set toolbar
        setSupportActionBar(list_toolbar)

        //custom font apply
        toolbar_title.typeface = keplarFont
        tv_empty_list.typeface=keplarFont
        tv_empty_filter_list.typeface=keplarFont

        //recyclerview setup
        recyclerView.layoutManager=LinearLayoutManager(baseContext)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        recyclerView.itemAnimator = SlideInLeftAnimator()
    }

    override fun showEmptyNoteScreen() {
        tv_empty_list.visibility = View.VISIBLE
    }

    override fun showEmptyResultScreen() {
        tv_empty_filter_list.visibility = View.VISIBLE
    }
}
