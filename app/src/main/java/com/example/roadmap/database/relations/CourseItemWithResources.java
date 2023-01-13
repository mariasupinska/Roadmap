package com.example.roadmap.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Resource;

import java.util.List;

//one to many relationship
public class CourseItemWithResources {
    @Embedded
    public CourseItem courseItem;

    @Relation(
            parentColumn = "courseItemId",
            entityColumn = "parentCourseItemId"
    )
    public List<Resource> resources;
}
