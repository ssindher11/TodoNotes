package com.ssindher11.todonotes

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.ssindher11.todonotes.db.NotesDatabase

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

    fun getNotesDb(): NotesDatabase = NotesDatabase.getInstance(this)

}