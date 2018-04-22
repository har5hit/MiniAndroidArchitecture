package com.justadeveloper96.helpers.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by harshith on 20/1/17.
 */

@Singleton
public class SharedPrefs {
    private static SharedPrefs sharedPrefs;

    private final SharedPreferences prefs;
    public static final String PREFERENCE = "github_prefs";


    @Inject
    public SharedPrefs(Context c) {
        prefs = c.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        Log.d(TAG, "SharedPrefs: created");
    }

    private static final String TAG = "SharedPrefs";

    public static void init(Context context){
        sharedPrefs=new SharedPrefs(context);
    }


    public void save(String id,String value)
    {
        getEditor().putString(id,value).apply();
    }
    public void save(String id,boolean value)
    {
        getEditor().putBoolean(id,value).apply();
    }
    public void save(String id,int value)
    {
        getEditor().putInt(id,value).apply();
    }
    public void save(String id,long value)
    {
        getEditor().putLong(id,value).apply();
    }

    public String getString(String id)
    {
        return prefs.getString(id,"");
    }
    public Long getLong(String id)
    {
        return prefs.getLong(id,0);
    }
    public int getInt(String id)
    {
        return prefs.getInt(id,0);
    }
    public boolean getBoolean(String id)
    {
        return prefs.getBoolean(id,false);
    }


    public void logout()
    {
        getEditor().clear().apply();
    }

    public final static Object lock=new Object();

    public static synchronized SharedPrefs getPrefs(Context context)
    {
        if(sharedPrefs==null)
        {
            synchronized (lock)
            {
                sharedPrefs=new SharedPrefs(context);
            }
        }
        return sharedPrefs;
    }

    public void setPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void removeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }


    private SharedPreferences.Editor getEditor(){
        return prefs.edit();
    }
}