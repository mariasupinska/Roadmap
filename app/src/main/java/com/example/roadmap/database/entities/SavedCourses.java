package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedCourses {
    @PrimaryKey
    public int savedCourseId;
    public int parentCourseId;
}
