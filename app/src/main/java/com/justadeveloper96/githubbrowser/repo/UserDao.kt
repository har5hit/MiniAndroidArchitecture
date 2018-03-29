package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import javax.inject.Singleton

/**
 * Created by harshith on 06-03-2018.
 */
@Singleton
@Dao
abstract class UserDao{
    @get:Query("SELECT * FROM User ORDER BY id ASC LIMIT 200")
    abstract val all: LiveData<List<User>>

    @Query("SELECT * FROM User WHERE login LIKE :search LIMIT 200")
    abstract fun find(search: String): List<User>

    @Query("SELECT Count(*) FROM User WHERE login LIKE :search")
    abstract fun count(search: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(User: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)

    @Update
    abstract fun update(user: User)

    @Delete
    abstract fun delete(user: User)

    @Query("DELETE FROM User")
    abstract fun destroy()
}