package com.example.tomek.simplenotes;

import java.util.Comparator;

/**
 * Created by Tomek on 08.03.2018.
 */

public class CompareNoteTitleAZ implements Comparator<Note> {
    @Override
    public int compare(Note n1, Note n2) {
        int title =  n1.getTitle().compareTo(n2.getTitle());
        if(title==0){
            return 0;
        }
        else return title;
    }
}
