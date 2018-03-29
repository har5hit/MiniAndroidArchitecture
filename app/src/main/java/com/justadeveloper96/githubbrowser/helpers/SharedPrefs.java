/*
package com.justadeveloper96.githubbrowser.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

*/
/**
 * Created by harshith on 20/1/17.
 *//*


public class SharedPrefs {
    private static SharedPrefs sharedPrefs;

    private final SharedPreferences prefs;
    public static final String PREFERENCE = "github_prefs";


    public SharedPrefs(Context c) {
        // editor = c.getSharedPreferences(c.getPackageName(), Context.MODE_PRIVATE).edit();
        prefs = c.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

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
                return sharedPrefs;
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
}*/
