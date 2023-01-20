package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roadmap.database.entities.SavedCourse;

import java.util.List;

@Dao
public interface SavedCourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSavedCourse(SavedCourse savedCourse);

    @Delete
    void deleteSavedCourse(SavedCourse savedCourse);

    @Update
    void updateSavedCourse(SavedCourse savedCourse);

    @Query("SELECT * FROM savedcourse")
    LiveData<List<SavedCourse>> findAllSavedCourses();
}
