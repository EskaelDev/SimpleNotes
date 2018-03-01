package com .example.tomek.simplenotes;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tomek on 22.02.2018.
 */

public class Note implements Serializable{
    private String title;
    private String content;
    private Date saveDate;
    private String color;


    public Note(String title, String content, Date saveDate, String color) {
        this.title = title;
        this.content = content;
        this.saveDate = saveDate;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

//    public void setContent(String content) {
//        this.content = content;
//    }

    public Date getSaveDate() {
        return saveDate;
    }

//    public void setSaveDate(Date saveDate) {
//        this.saveDate = saveDate;
//    }

    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }
}
