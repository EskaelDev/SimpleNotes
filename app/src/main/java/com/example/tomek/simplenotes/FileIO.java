package com.example.tomek.simplenotes;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Tomek on 22.02.2018.
 */

public class FileIO {
    private static final String FILE_EXTENSION = ".snt";

    public static boolean SaveNote(Context context, Note note) {
        String filename = String.valueOf(note.getTitle()) + " " + String.valueOf(note.getSaveDate()) + FILE_EXTENSION;

        FileOutputStream fileOutS;
        ObjectOutputStream objOutS;
        try {
            fileOutS = context.openFileOutput(filename, context.MODE_PRIVATE);
            objOutS = new ObjectOutputStream(fileOutS);
            objOutS.writeObject(note);
            objOutS.close();
            fileOutS.close();
        } catch (Exception e) {
            Log.e("Error",e.getMessage());
            return false;
        }
        return true;
    }
}
