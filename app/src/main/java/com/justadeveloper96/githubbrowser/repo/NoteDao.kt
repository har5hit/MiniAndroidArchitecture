package com.justadeveloper96.githubbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import javax.inject.Singleton

/**
 * Created by harshith on 06-03-2018.
 */
@Singleton
@Dao
abstract class NoteDao{
    @get:Query("SELECT * FROM Note ORDER BY createdAt DESC")
    abstract val all: LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE id LIKE :search")
    abstract fun find(search: String): List<Note>

    @Query("SELECT Count(*) FROM Note WHERE Note LIKE :search")
    abstract fun count(search: String): Int

    @Insert
    abstract fun insertAll(note: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(note: Note)

    @Update
    abstract fun update(note: Note)

    @Delete
    abstract fun delete(note: Note)

    @Query("DELETE FROM Note")
    abstract fun destroy()
}