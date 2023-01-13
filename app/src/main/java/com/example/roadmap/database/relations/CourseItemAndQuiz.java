package com.example.roadmap.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Quiz;


//one to one relationship
public class CourseItemAndQuiz {
    @Embedded
    public CourseItem courseItem;

    @Relation(
            parentColumn = "courseItemId",
            entityColumn = "parentCourseItemId"
    )
    public Quiz quiz;
}
