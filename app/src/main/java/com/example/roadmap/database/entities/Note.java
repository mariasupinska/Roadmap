package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    public int noteId;
    public String noteText;
}
