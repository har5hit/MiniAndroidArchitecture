package com.justadeveloper96.helpers.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by harshith on 20/1/17.
 */

@Singleton
class SharedPrefs {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Inject
    public SharedPrefs(Context context) {
        editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void save(String id,String value)
    {
        editor.putString(id,value).commit();
    }
    public void save(String id,Boolean value)
    {
        editor.putBoolean(id,value).commit();
    }
    public void save(String id,int value)
    {
        editor.putInt(id,value).commit();
    }
    public void save(String id,long value)
    {
        editor.putLong(id,value).commit();
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
        editor.clear().commit();
    }

    public void setPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void removeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }
}