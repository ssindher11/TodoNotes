package com.ssindher11.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ssindher11.todonotes.NotesApp
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.adapter.NotesAdapter
import com.ssindher11.todonotes.clicklisteners.ItemClickListener
import com.ssindher11.todonotes.db.Notes
import com.ssindher11.todonotes.utils.AppConstant
import com.ssindher11.todonotes.utils.PrefConstant
import com.ssindher11.todonotes.workmanager.MyWorker
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {

    private lateinit var fabAddNotes: FloatingActionButton
    private lateinit var notesRV: RecyclerView
    private lateinit var nameTV: TextView
    private lateinit var menuIB: ImageButton
    private lateinit var noNotesIV: ImageView
    private lateinit var noNotesTV: TextView

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
        setupWorkManager()

        nameTV.text = fullName

        fabAddNotes.setOnClickListener {
            startActivityForResult(Intent(this@MyNotesActivity, AddNotesActivity::class.java), ADD_NOTES_CODE)
        }
    }

    private fun bindViews() {
        notesRV = findViewById(R.id.rv_notes)
        fabAddNotes = findViewById(R.id.fab_add_notes)
        nameTV = findViewById(R.id.tv_name)
        menuIB = findViewById(R.id.ib_menu)
        noNotesIV = findViewById(R.id.iv_no_notes)
        noNotesTV = findViewById(R.id.tv_no_notes)
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
                intent.putExtra(AppConstant.IMAGE_PATH, notes.imagePath)
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

        if (notesList.size != 0) {
            notesRV.visibility = View.VISIBLE
            noNotesIV.visibility = View.GONE
            noNotesTV.visibility = View.GONE
        }
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

            if (!title.isNullOrBlank() && !description.isNullOrBlank()) {
                val notes = Notes(title = title, description = description, imagePath = imagePath!!, isTaskCompleted = false)
                addNotesToDb(notes)
                notesList.add(notes)
                notesRV.adapter?.notifyItemChanged(notesList.size - 1)
                if (notesList.size != 0) {
                    notesRV.visibility = View.VISIBLE
                    noNotesIV.visibility = View.GONE
                    noNotesTV.visibility = View.GONE
                }
            }
        }
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)
    }

    fun setupMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.itemBlog -> {
                    startActivity(Intent(this@MyNotesActivity, BlogActivity::class.java))
                    true
                }
                R.id.logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun logout() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, false)
        editor.apply()

        startActivity(Intent(this@MyNotesActivity, LoginActivity::class.java))
        finishAfterTransition()
    }
}