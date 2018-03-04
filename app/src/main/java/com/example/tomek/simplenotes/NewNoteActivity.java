package com.example.tomek.simplenotes;

import android.content.DialogInterface;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class NewNoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteContent;

    private String noteFileName;
    private Note loadedNote;

    private String noteColor = "noBG";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = getIntent().getStringExtra("action");
        setTitle(title);

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
                if (noteTitle.getText().length() == 0 || noteContent.getText().length() == 0) {
                    Toast.makeText(this, "Title/Content is required", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    SaveNote();
                    break;
                }
            case R.id.action_note_delete:
                DeleteNote();
                break;
            case R.id.action_note_share:
                ShareNote();
                break;
        }
        return true;
    }

    private void SaveNote() {
        Note note;
        if (loadedNote == null) {
            note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), Calendar.getInstance().getTime(), noteColor);
        } else {
            note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), loadedNote.getSaveDate(), noteColor);
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
                            FileIO.DeleteNote(getApplicationContext(), loadedNote.getSaveDate() + FileIO.FILE_EXTENSION);
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

    public void colorPick(View view) {
        switch (view.getId()) {
            case R.id.red:
                noteColor = "red";
                Toast.makeText(this, "Color: " + noteColor, Toast.LENGTH_SHORT).show();
                break;

            case R.id.yellow:
                noteColor = "yellow";
                Toast.makeText(this, "Color: " + noteColor, Toast.LENGTH_SHORT).show();
                break;

            case R.id.green:
                noteColor = "green";
                Toast.makeText(this, "Color: " + noteColor, Toast.LENGTH_SHORT).show();
                break;

            case R.id.blue:
                noteColor = "blue";
                Toast.makeText(this, "Color: " + noteColor, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void ShareNote(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        StringBuilder shareContent = new StringBuilder();
        shareContent.append(noteTitle.getText().toString());
        shareContent.append(System.getProperty("line.separator"));
        shareContent.append(System.getProperty("line.separator"));
        shareContent.append(noteContent.getText().toString());
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent.toString());
        startActivity(Intent.createChooser(shareIntent, "Share note"));
    }
}
