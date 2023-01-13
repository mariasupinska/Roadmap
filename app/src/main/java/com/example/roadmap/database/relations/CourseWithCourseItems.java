package com.example.roadmap.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.entities.CourseItem;

import java.util.List;

//one to many relationship
public class CourseWithCourseItems {
    @Embedded
    public Course course;

    @Relation(
            parentColumn = "courseId",
            entityColumn = "parentCourseId"
    )
    public List<CourseItem> courseItems;
}
