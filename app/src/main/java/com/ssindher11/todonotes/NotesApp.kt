package com.ssindher11.todonotes

import android.app.Application
import com.ssindher11.todonotes.db.NotesDatabase

class NotesApp : Application() {

    /*fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }*/

    fun getNotesDb(): NotesDatabase = NotesDatabase.getInstance(this)

}