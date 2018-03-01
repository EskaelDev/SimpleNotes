package com.example.tomek.simplenotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

    private ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        notesList = findViewById(R.id.notes_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_new:
                Intent noteIntent = new Intent(this, NewNoteActivity.class);
                startActivity(noteIntent);
                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesList.setAdapter(null);
        ArrayList<Note> notes = FileIO.LoadNotes(this);

        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "No saved notes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            NoteAdapter noteAdapter = new NoteAdapter(this, R.layout.item_note, notes);
            notesList.setAdapter(noteAdapter);

            notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String fileName = ((Note)notesList.getItemAtPosition(i)).getSaveDate() + FileIO.FILE_EXTENSION;

                    Intent editNoteIntent = new Intent(getApplicationContext(), NewNoteActivity.class);
                    editNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(editNoteIntent);
                }
            });
        }
    }
}
