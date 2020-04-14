package com.ssindher11.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
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

        fabAddNotes.setOnClickListener { setupDialogBox() }
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

    private fun setupDialogBox() {
        val view: View = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val titleET: EditText = view.findViewById(R.id.et_title)
        val descET: EditText = view.findViewById(R.id.et_description)
        val submitBtn: MaterialButton = view.findViewById(R.id.btn_submit)

        val dialog: AlertDialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        submitBtn.setOnClickListener {
            val title = titleET.text.toString()
            val desc = descET.text.toString()
            if (title.isNotBlank() and desc.isNotBlank()) {
                val notes = Notes(title = title, description = desc)
                notesList.add(notes)
                addNotesToDb(notes)
            } else {
                Toast.makeText(this, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
            }
            dialog.hide()
        }

        dialog.show()
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
}