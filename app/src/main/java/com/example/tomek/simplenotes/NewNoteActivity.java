package com.example.tomek.simplenotes;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        noteTitle = findViewById(R.id.new_note_title);
        noteContent = findViewById(R.id.new_note_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_note_save:
                SaveNote();
                break;
        }

        return true;
    }

    private void SaveNote(){
        Note note = new Note(noteTitle.getText().toString(), noteContent.getText().toString(), Calendar.getInstance().getTime(), "");
        if(FileIO.SaveNote(this ,note))
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Note couldn't be saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
