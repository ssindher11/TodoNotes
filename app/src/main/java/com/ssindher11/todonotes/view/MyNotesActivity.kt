package com.ssindher11.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ssindher11.todonotes.NotesApp
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.adapter.NotesAdapter
import com.ssindher11.todonotes.clicklisteners.ItemClickListener
import com.ssindher11.todonotes.db.Notes
import com.ssindher11.todonotes.utils.AppConstant
import com.ssindher11.todonotes.utils.PrefConstant

class MyNotesActivity : AppCompatActivity() {

    private lateinit var fabAddNotes: FloatingActionButton
    private lateinit var notesRV: RecyclerView
    private lateinit var nameTV: TextView

    lateinit var sharedPreferences: SharedPreferences

    private val ADD_NOTES_CODE = 100

    private var fullName: String? = null
    private var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindViews()
        setupSharedPreferences()
        getIntentData()
        getDataFromDatabase()
        setupRecyclerView()

        nameTV.text = fullName

        fabAddNotes.setOnClickListener {
            // setupDialogBox()
            startActivityForResult(Intent(this@MyNotesActivity, AddNotesActivity::class.java), ADD_NOTES_CODE)
        }
    }

    private fun bindViews() {
        notesRV = findViewById(R.id.rv_notes)
        fabAddNotes = findViewById(R.id.fab_add_notes)
        nameTV = findViewById(R.id.tv_name)
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        }
        if (fullName.isNullOrBlank()) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "")!!
        }
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun setupRecyclerView() {
        val itemClickListener: ItemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        notesRV.layoutManager = layoutManager
        notesRV.adapter = notesAdapter
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTES_CODE) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val notes = Notes(title = title!!, description = description!!, imagePath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
            notesList.add(notes)
            notesRV.adapter?.notifyItemChanged(notesList.size - 1)
        }
    }
}