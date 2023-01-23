package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.relations.CourseItemAndQuiz;
import com.example.roadmap.database.relations.CourseItemWithResources;

import java.util.List;

@Dao
public interface CourseItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseItem(CourseItem courseItem);

    @Delete
    void deleteCourseItem(CourseItem courseItem);

    @Update
    void updateCourseItem(CourseItem courseItem);

    @Query("SELECT * FROM courseitem")
    LiveData<List<CourseItem>> findAllCourseItems();

    @Query("SELECT COUNT(*) FROM resource WHERE parentCourseItemId = :courseItemID")
    public int getResourcesAmount(int courseItemID);

    @Transaction
    @Query("SELECT * FROM courseitem WHERE courseItemId = :courseItemID")
    public CourseItemWithResources getCourseItemWithResources(int courseItemID);

    @Transaction
    @Query("SELECT * FROM courseitem")
    public List<CourseItemAndQuiz> getCourseItemsAndQuizes();

    @Transaction
    @Query("SELECT * FROM courseitem WHERE courseItemId = :courseItemID")
    public CourseItemAndQuiz getCourseItemWithQuizByCourseItemId(int courseItemID);

    @Transaction
    @Query("SELECT * FROM courseitem WHERE isFavourite = 1")
    public List<CourseItem> getFavouriteCourseItems();
}
