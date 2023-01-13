package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey
    public int courseId;
    public String courseCategory;
    public String courseName;
}
