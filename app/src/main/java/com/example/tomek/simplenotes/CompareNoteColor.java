package com.example.tomek.simplenotes;

import java.util.Comparator;

/**
 * Created by Tomek on 08.03.2018.
 */

public class CompareNoteColor implements Comparator<Note> {
    @Override
    public int compare(Note n1, Note n2) {
        return n1.getColor().compareTo(n2.getColor());
    }
}
