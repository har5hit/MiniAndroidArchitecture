package com.justadeveloper96.githubbrowser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.justadeveloper96.githubbrowser.di.MyApp
import com.justadeveloper96.githubbrowser.helpers.Constants
import com.justadeveloper96.githubbrowser.list.ListActivity
import com.justadeveloper96.helpers.di.SharedPrefs
import javax.inject.Inject


/**
 * Created by harshith on 07-03-2018.
 */
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        (applicationContext as MyApp).injector.inject(this)

        checkAndLaunch()
    }

    override fun onResume() {
        super.onResume()
        checkAndLaunch()
    }

    fun checkAndLaunch(){
        if(!sharedPrefs.getString(Constants.OAUTH_TOKEN).isNullOrEmpty())
        {
            (applicationContext as MyApp).reinitWithToken(sharedPrefs.getString(Constants.OAUTH_TOKEN))
            startActivity(Intent(this,ListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    fun githubLogin(v: View) {
        com.hardikgoswami.oauthLibGithub.GithubOauth
                .Builder()
                .withClientId(Constants.GITHUB_CLIENT_ID)
                .withClientSecret(Constants.GITHUB_CLIENT_SECRET)
                .withContext(this)
                .packageName(packageName)
                .nextActivity(packageName+".SplashActivity")
                .debug(true)
                .execute()
    }
}
