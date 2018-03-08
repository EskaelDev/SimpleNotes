package com.example.tomek.simplenotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NotesListActivity extends AppCompatActivity {

    private ListView notesList;
    ArrayList<Note> notes;
    //    String sortType;

    boolean sorted;
    //    enum SortType {
//        ASC, DESC, COL, AZ, ZA
//    }
//
//    SortType sortType;
    String sortingType;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        notesList = findViewById(R.id.notes_list);
        registerForContextMenu(notesList);
//        sortType = SortType.ASC;

        sortingType = getIntent().getStringExtra("sorttype");
        sorted = getIntent().getBooleanExtra("issorted", true);
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
                noteIntent.putExtra("action", "New Note");
                startActivity(noteIntent);
                break;
            case R.id.action_sort:
                PopupMenu popup = new PopupMenu(getApplicationContext(), findViewById(R.id.action_sort));
                popup.getMenuInflater().inflate(R.menu.menu_sort_option, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//                        sorted=false;
//                        Toast.makeText(NotesListActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.sort_date_asc:
                                RefreshSorted("asc");
                                break;
                            case R.id.sort_date_desc:
                                RefreshSorted("desc");
                                break;
                            case R.id.sort_colors:
                                RefreshSorted("col");
                                break;
                            case R.id.sort_title_AZ:
                                RefreshSorted("az");
                                break;
                            case R.id.sort_title_ZA:
                                RefreshSorted("za");
                                break;
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu

//                Intent settingsIntent = new Intent(this, SettingsActivity.class);
//                startActivity(settingsIntent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesList.setAdapter(null);
        notes = FileIO.LoadNotes(this);
        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "No saved notes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!sorted)
                SortNotesBy(sortingType);

            NoteAdapter noteAdapter = new NoteAdapter(this, R.layout.item_note, notes);
            notesList.setAdapter(noteAdapter);


            notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String fileName = ((Note) notesList.getItemAtPosition(i)).getSaveDate() + FileIO.FILE_EXTENSION;

                    Intent editNoteIntent = new Intent(getApplicationContext(), NewNoteActivity.class);
                    editNoteIntent.putExtra("NOTE_FILE", fileName);
                    editNoteIntent.putExtra("action", "Edit Note");
                    startActivity(editNoteIntent);
                }
            });


//            notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    openContextMenu();
//                    unregisterForContextMenu(notesList);
//
//                    return false;
//                }
//            });


        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.notes_list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_note_option, menu);
        }
    }

    //
//
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            // ((Note) notesList.getItemAtPosition(info.position))
            case R.id.option_share:
                ShareNote(((Note) notesList.getItemAtPosition(info.position)));
                return true;

            case R.id.option_duplicate:
                DuplicateNote(((Note) notesList.getItemAtPosition(info.position)));

                return true;

            case R.id.option_delete:
                DeleteNote(((Note) notesList.getItemAtPosition(info.position)));
                return true;

            default:
                return false;
//                        super.onContextItemSelected(item);
        }
    }

    private void ShareNote(Note note) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        StringBuilder shareContent = new StringBuilder();
        shareContent.append(note.getTitle());
        shareContent.append(System.getProperty("line.separator"));
        shareContent.append(System.getProperty("line.separator"));
        shareContent.append(note.getContent());
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent.toString());
        startActivity(Intent.createChooser(shareIntent, "Share note"));
    }

    private void DeleteNote(final Note note) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("You are about to delete this note: " + note.getTitle() + ", are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FileIO.DeleteNote(getApplicationContext(), note.getSaveDate() + FileIO.FILE_EXTENSION);
                        Toast.makeText(getApplicationContext(),
                                note.getTitle() + " deleted",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);

        dialog.show();

    }

    private void DuplicateNote(Note note) {
        Note newNote = new Note(note.getTitle(), note.getContent(), Calendar.getInstance().getTime(), note.getColor());
        FileIO.SaveNote(this, newNote);
        recreate();
    }

    private void SortNotesBy(String sortType) {
        switch (sortType) {
            case "asc":
                Collections.sort(notes, new CompareNoteDateASC());
                break;
            case "desc":
                Collections.sort(notes, new CompareNoteDateDESC());
                break;
            case "col":
                Collections.sort(notes, new CompareNoteColor());
                break;
            case "az":
                Collections.sort(notes, new CompareNoteTitleAZ());
                break;
            case "za":
                Collections.sort(notes, new CompareNoteTitleAZ());
                Collections.reverse(notes);
                break;
        }
    }

    private void RefreshSorted(String sortType) {
        Intent intent = new Intent(this, NotesListActivity.class);
        intent.putExtra("sorttype", sortType);
        intent.putExtra("issorted", false);
        startActivity(intent);
        finish();
    }
}
