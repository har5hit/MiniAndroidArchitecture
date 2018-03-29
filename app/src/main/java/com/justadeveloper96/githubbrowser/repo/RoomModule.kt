package com.justadeveloper96.githubbrowser.repo

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.justadeveloper96.githubbrowser.repo.RoomModule.Companion.getDatabase
import com.justadeveloper96.helpers.Utils.context
import com.justadeveloper96.helpers.di.AppModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by harshith on 06-03-2018.
 */
@Module(includes = arrayOf(AppModule::class))
public class RoomModule(){
    companion object {
        fun getDatabase(context: Context): DatabaseModule {
            return Room.databaseBuilder(context.getApplicationContext(), DatabaseModule::class.java, "my_users_db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    @Singleton
    @Provides
    fun providesRoomDb(context: Context):DatabaseModule= getDatabase(context)

    @Singleton
    @Provides
    fun providesUsersDao(db:DatabaseModule):UserDao = db.usersDao()

}