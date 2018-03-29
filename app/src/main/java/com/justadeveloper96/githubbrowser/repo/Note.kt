package com.justadeveloper96.githubbrowser.repo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by harshith on 06-03-2018.
 */
@Entity
class Note(){
    @PrimaryKey(autoGenerate = true) var id:Int=0
    var createdAt:Long = System.currentTimeMillis()
    var updatedAt:Long
    var liked:Boolean = false
    var starred: Boolean = false
    var title:String=""
    var note:String=""
    init {
        updatedAt=createdAt
    }

    override fun toString(): String {
        return "Note(title='$title', note='$note', id=$id, createdAt=$createdAt, updatedAt=$updatedAt, liked=$liked, starred=$starred)"
    }

}