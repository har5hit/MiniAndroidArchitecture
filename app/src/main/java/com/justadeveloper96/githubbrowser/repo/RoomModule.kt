package com.justadeveloper96.githubbrowser.repo

import android.arch.persistence.room.Room
import android.content.Context
import com.justadeveloper96.githubbrowser.repo.db.UserDao
import com.justadeveloper96.helpers.di.AppModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by harshith on 06-03-2018.
 */
@Module
public class RoomModule(){

    @Singleton
    @Provides
    fun providesRoomDb(context: Context):AppRoomDatabase= AppRoomDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesUsersDao(db:AppRoomDatabase): UserDao = db.usersDao()

}