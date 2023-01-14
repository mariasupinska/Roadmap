package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CourseItem {
    @PrimaryKey
    public int courseItemId;
    public String courseItemName;
    public String courseItemDescription;
    public int parentCourseId;

    public CourseItem(int courseItemId, String courseItemName, String courseItemDescription, int parentCourseId) {
        this.courseItemId = courseItemId;
        this.courseItemName = courseItemName;
        this.courseItemDescription = courseItemDescription;
        this.parentCourseId = parentCourseId;
    }
}
