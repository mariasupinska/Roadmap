package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.relations.CourseWithCourseItems;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM course")
    LiveData<List<Course>> findAllCourses();

    @Transaction
    @Query("SELECT * FROM course")
    public List<CourseWithCourseItems> getCoursesWithCourseItems();
}
