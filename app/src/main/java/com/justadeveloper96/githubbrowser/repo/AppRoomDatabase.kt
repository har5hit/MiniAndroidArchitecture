package com.justadeveloper96.githubbrowser.repo
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.justadeveloper96.githubbrowser.repo.db.User
import com.justadeveloper96.githubbrowser.repo.db.UserDao

/**
 * Created by harshith on 06-03-2018.
 */
@Database(entities = [(User::class)], version = 6)
abstract class AppRoomDatabase : RoomDatabase() {

    companion object {
        fun getDatabase(context: Context): AppRoomDatabase {
            return Room.databaseBuilder(context.applicationContext, AppRoomDatabase::class.java, "my_users_db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    abstract fun usersDao(): UserDao
}