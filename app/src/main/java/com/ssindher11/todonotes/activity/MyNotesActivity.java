package com.ssindher11.todonotes.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssindher11.todonotes.AppConstants;
import com.ssindher11.todonotes.R;
import com.ssindher11.todonotes.adapter.NotesAdapter;
import com.ssindher11.todonotes.clicklisteners.ItemClickListener;
import com.ssindher11.todonotes.model.Notes;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    private FloatingActionButton fabAddNotes;
    private RecyclerView notesRV;

    String fullName;
    ArrayList<Notes> notesList = new ArrayList<>();

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        bindViews();
        setupSharedPreference();
        getIntentData();

        getSupportActionBar().setTitle(fullName);


        fabAddNotes.setOnClickListener(v -> setupDialogBox());
    }

    private void bindViews() {
        notesRV = findViewById(R.id.rv_notes);
        fabAddNotes = findViewById(R.id.fab_add_notes);
    }

    private void setupSharedPreference() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstants.FULL_NAME);
        if (TextUtils.isEmpty(fullName)) {
            fullName = sharedPreferences.getString(AppConstants.FULL_NAME, "");
        }
    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout, null);
        final EditText titleET = view.findViewById(R.id.et_title);
        final EditText descET = view.findViewById(R.id.et_description);
        final MaterialButton submitBtn = view.findViewById(R.id.btn_submit);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        submitBtn.setOnClickListener(v -> {
            String title = titleET.getText().toString();
            String desc = descET.getText().toString();
            if (!title.trim().isEmpty() && !desc.trim().isEmpty()) {
                notesList.add(new Notes(title, desc));
            } else {
                Toast.makeText(this, "Title or Description can't be empty", Toast.LENGTH_SHORT).show();
            }
            setupRecyclerView();
            dialog.hide();
        });

        dialog.show();
    }

    private void setupRecyclerView() {
        ItemClickListener itemClickListener = notes -> {
            Intent intent = new Intent(MyNotesActivity.this, DetailActivity.class);
            intent.putExtra(AppConstants.TITLE, notes.getTitle());
            intent.putExtra(AppConstants.DESCRIPTION, notes.getDescription());
            startActivity(intent);
        };
        NotesAdapter notesAdapter = new NotesAdapter(notesList, itemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyNotesActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        notesRV.setLayoutManager(layoutManager);
        notesRV.setAdapter(notesAdapter);
    }

}
