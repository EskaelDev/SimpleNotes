package com.example.tomek.simplenotes;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

/**
 * Created by Tomek on 24.02.2018.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    public Context context;

    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public void sort(@NonNull Comparator<? super Note> comparator) {
        super.sort(comparator);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, null);
        }
        Note note = getItem(position);

        if (note != null) {
            TextView title = convertView.findViewById(R.id.item_note_title);
            TextView date = convertView.findViewById(R.id.item_note_date);
            TextView content = convertView.findViewById(R.id.item_note_content);
            LinearLayout noteLayout = convertView.findViewById(R.id.item_note);

            DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());

            Resources res = context.getResources();

            switch (note.getColor()) {
                case "red":
                    noteLayout.setBackgroundColor(res.getColor(android.R.color.holo_red_dark));
                    break;
                case "blue":
                    noteLayout.setBackgroundColor(res.getColor(android.R.color.holo_blue_dark));
                    break;
                case "green":
                    noteLayout.setBackgroundColor(res.getColor(android.R.color.holo_green_light));
                    break;
                case "yellow":
                    noteLayout.setBackgroundColor(res.getColor(android.R.color.holo_orange_light));
                    break;
                case "noBG":
                    noteLayout.setBackgroundColor(res.getColor(R.color.appBG1));
                    break;
            }
            title.setText(note.getTitle());
            date.setText(dateFormat.format(note.getSaveDate()));
            date.append("  ");
            date.append(timeFormat.format(note.getSaveDate()));

                content.setText(note.getContent());
        }

        return convertView;
    }
}
