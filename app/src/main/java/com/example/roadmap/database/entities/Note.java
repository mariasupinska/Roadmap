package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    public int noteId;
    public String noteTitle;
    public String noteText;

    public Note(int noteId, String noteTitle, String noteText) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }
}
