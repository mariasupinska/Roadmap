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
    public int isFavourite;
    public CourseItem(int courseItemId, String courseItemName, String courseItemDescription, int parentCourseId, int isFavourite) {
        this.courseItemId = courseItemId;
        this.courseItemName = courseItemName;
        this.courseItemDescription = courseItemDescription;
        this.parentCourseId = parentCourseId;
        this.isFavourite = isFavourite;
    }
}
