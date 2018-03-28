package com.justadeveloper96.githubbrowser

import android.app.ListActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.hardikgoswami.oauthLibGithub.GithubOauth



/**
 * Created by harshith on 07-03-2018.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        /*Handler().postDelayed({
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        },500)*/


    }

    fun githubLogin(v: View) {
        GithubOauth
                .Builder()
                .withClientId("f870d55e3d312324de97")
                .withClientSecret("916577d9a0a5c1ed05b4d0172914566342cb3fb1")
                .withContext(applicationContext)
                .packageName(packageName)
                .nextActivity(packageName+".list.ListActivity")
                .execute()
    }
}
