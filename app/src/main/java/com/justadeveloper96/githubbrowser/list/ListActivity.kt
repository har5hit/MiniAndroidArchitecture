package com.justadeveloper96.githubbrowser.list

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Resources
import android.databinding.DataBindingUtil.setContentView
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_notes_list.*
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.CheckedTextView
import android.widget.Toast
import com.justadeveloper96.githubbrowser.R
import com.justadeveloper96.githubbrowser.R.id.tv_empty_filter_list
import com.justadeveloper96.githubbrowser.di.MyApp
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.githubbrowser.helpers.Font
import com.justadeveloper96.githubbrowser.helpers.Utils
import com.justadeveloper96.githubbrowser.list.IList
import com.justadeveloper96.githubbrowser.repo.Note
import com.justadeveloper96.helpers.Utils.getThemeColor
import com.justadeveloper96.helpers.arch.Resource
import com.justadeveloper96.helpers.arch.Status
import com.justadeveloper96.helpers.di.DaggerViewModelFactory
import com.justadeveloper96.helpers.di.SharedPrefs
import com.readystatesoftware.chuck.internal.ui.MainActivity
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * Created by harshith on 06-03-2018.
 */

class ListActivity: AppCompatActivity(), IList.View {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    override fun showLoading(loading: Boolean) {
        tv_empty_filter_list.visibility = if (loading) View.VISIBLE else View.GONE
    }

    var userName:String?=null
    override fun isFilterApplied(): Boolean = !userName.isNullOrEmpty();

    private val keplarFont by lazy {  Font.getFont(baseContext, Constants.keplarStdFont) }
    private val adapter by lazy { ListAdapter() }
    private val viewmodel by lazy { ViewModelProviders.of(this,viewModelFactory).get(ListViewModel::class.java) as IList.Actions }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        (applicationContext as MyApp).injector.inject(this)

        Toast.makeText(applicationContext,sharedPrefs.getString(Constants.OAUTH_TOKEN),Toast.LENGTH_LONG).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        val mSearchItem = menu?.findItem(R.id.m_search);

        MenuItemCompat.setOnActionExpandListener(mSearchItem, object :MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                // Called when SearchView is expanding
                animateSearchToolbar(1, true, true);
                return true;            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                if (mSearchItem!!.isActionViewExpanded()) {
                    animateSearchToolbar(1, false, false);
                }
                return true;
            }
        });

        return true;
    }

    private fun reset() {
        tv_empty_filter_list.visibility = View.GONE
        tv_empty_list.visibility = View.GONE
        showLoading(false)
    }

    private fun initViews() {
        //set toolbar
        setSupportActionBar(toolbar)
        //custom font apply
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


    fun animateSearchToolbar( numberOfMenuIcon:Int , containsOverflow:Boolean, show:Boolean)
    {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        //mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.quantum_grey_600));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.quantum_grey_600));

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width = toolbar . getWidth () -
                        ( if(containsOverflow) getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        ((resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2)
                val createCircularReveal = ViewAnimationUtils . createCircularReveal (toolbar,
                        if (isRtl(resources))  toolbar.getWidth() - width else width, toolbar.getHeight() / 2, 0.0f,  width.toFloat());

                createCircularReveal.setDuration(250);
                createCircularReveal.start();
            } else {
                val translateAnimation = TranslateAnimation(0.0f, 0.0f, (-toolbar.getHeight()).toFloat(), 0.0f);
                translateAnimation.setDuration(220);
                toolbar.clearAnimation();
                toolbar.startAnimation(translateAnimation);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width = toolbar . getWidth () -
                        ( if(containsOverflow)  getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0)-
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                val createCircularReveal = ViewAnimationUtils . createCircularReveal (toolbar,
                        if (isRtl(getResources())) toolbar.getWidth()-width else width, toolbar.getHeight() / 2,  width.toFloat(), 0.0f);
                createCircularReveal.setDuration(250);
                createCircularReveal.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        toolbar.setBackgroundColor(getThemeColor(baseContext, R.attr.colorPrimary));
                        // mDrawerLayout.setStatusBarBackgroundColor(getThemeColor(baseContext, R.attr.colorPrimaryDark));
                        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.quantum_grey_600));

                    }
                });
                createCircularReveal.start();
            } else {
                val alphaAnimation =  AlphaAnimation(1.0f, 0.0f);
                val translateAnimation = TranslateAnimation(0.0f, 0.0f, 0.0f, (-toolbar.getHeight()).toFloat());
                val animationSet = AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(220);
                animationSet.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        toolbar.setBackgroundColor(getThemeColor(baseContext, R.attr.colorPrimary));
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                });
                toolbar.startAnimation(animationSet);
            }
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.quantum_grey_600));
            //   mDrawerLayout.setStatusBarBackgroundColor(ThemeUtils.getThemeColor(MainActivity.this, R.attr.colorPrimaryDark));
        }
    }

    private fun isRtl(resources: Resources): Boolean {
        return resources.getConfiguration().getLayoutDirection() === View.LAYOUT_DIRECTION_RTL
    }
}
