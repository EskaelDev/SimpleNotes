package com.example.tomek.simplenotes;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteContent;

    private String noteFileName;
    private Note loadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        noteTitle = findViewById(R.id.new_note_title);
        noteContent = findViewById(R.id.new_note_content);

        noteFileName = getIntent().getStringExtra("NOTE_FILE");
        if (noteFileName != null && !noteFileName.isEmpty()) {
            loadedNote = FileIO.getNoteByName(this, noteFileName);

            if (loadedNote != null) {
                noteTitle.setText(loadedNote.getTitle());
                noteContent.setText(loadedNote.getContent());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_note_save:
                if (noteTitle.getText().length()==0 || noteContent.getText().length() == 0) {
                    Toast.makeText(this, "Title/Content is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    SaveNote();
                    break;
                }
            case R.id.action_note_delete:
                DeleteNote();
                break;
        }
        return true;
    }

    private void SaveNote() {
        Note note;
        if (loadedNote == null) {
            note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), Calendar.getInstance().getTime(), "");
        } else {
            note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), loadedNote.getSaveDate(), "");
        }
        if (FileIO.SaveNote(this, note))
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Note couldn't be saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void DeleteNote() {
        if (loadedNote == null) {
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("You are about to delete this note: " + loadedNote.getTitle() + ", are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FileIO.DeleteNote(getApplicationContext(), loadedNote.getTitle() + " " + loadedNote.getSaveDate() + FileIO.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(),
                                    loadedNote.getTitle() + " deleted",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
            .setCancelable(false);

            dialog.show();
        }
    }
}
