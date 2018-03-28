package com.justadeveloper96.githubbrowser.repo
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by harshith on 06-03-2018.
 */
@Database(entities = [(Note::class)], version = 5)
abstract class DatabaseModule : RoomDatabase() {
    abstract fun notesDao(): NoteDao
}