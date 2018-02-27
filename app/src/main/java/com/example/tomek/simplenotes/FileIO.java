package com.example.tomek.simplenotes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tomek on 22.02.2018.
 */

public class FileIO {
    public static final String FILE_EXTENSION = ".snt";

    public static boolean SaveNote(Context context, Note note) {
        String filename = String.valueOf(String.valueOf(note.getSaveDate()) + FILE_EXTENSION);

        FileOutputStream fileOutS;
        ObjectOutputStream objOutS;
        try {
            fileOutS = context.openFileOutput(filename, context.MODE_PRIVATE);
            objOutS = new ObjectOutputStream(fileOutS);
            objOutS.writeObject(note);
            objOutS.close();
            fileOutS.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            return false;
        }
        return true;
    }

    public static ArrayList<Note> LoadNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for (String file : filesDir.list()) {
            if (file.endsWith(FILE_EXTENSION)) {
                noteFiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < noteFiles.size(); i++) {
            try {
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);

                notes.add((Note) ois.readObject());

                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    public static Note getNoteByName(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        Note note;

        if (file.exists()) {

            FileInputStream fis;
            ObjectInputStream ois;

            try {
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);

                note = (Note) ois.readObject();

                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            return note;
        }
return null;
    }

    public static void DeleteNote(Context context, String fileName) {
        File directory = context.getFilesDir();
        File file = new File(directory, fileName);

        if(file.exists()){
            file.delete();
        }
    }
}
