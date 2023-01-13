package com.example.roadmap.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Note;
import com.example.roadmap.database.entities.Question;
import com.example.roadmap.database.entities.Quiz;
import com.example.roadmap.database.entities.Resource;
import com.example.roadmap.database.relations.CourseItemAndQuiz;
import com.example.roadmap.database.relations.CourseItemWithResources;
import com.example.roadmap.database.relations.CourseWithCourseItems;
import com.example.roadmap.database.relations.QuizWithQuestions;

import java.util.List;

@Dao
public interface RoadmapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);
    @Delete
    void deleteCourse(Course course);
    @Update
    void updateCourse(Course course);
    @Query("SELECT * FROM course")
    LiveData<List<Course>> findAllCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseItem(CourseItem courseItem);
    @Delete
    void deleteCourseItem(CourseItem courseItem);
    @Update
    void updateCourseItem(CourseItem courseItem);
    @Query("SELECT * FROM courseitem")
    LiveData<List<CourseItem>> findAllCourseItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Update
    void updateNote(Note note);
    @Query("SELECT * FROM note")
    LiveData<List<Note>> findAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuiz(Quiz quiz);
    @Delete
    void deleteQuiz(Quiz quiz);
    @Update
    void updateQuiz(Quiz quiz);
    @Query("SELECT * FROM quiz")
    LiveData<List<Quiz>> findAllQuizes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);
    @Delete
    void deleteQuestion(Question question);
    @Update
    void updateQuestion(Question question);
    @Query("SELECT * FROM question")
    LiveData<List<Question>> findAllQuestions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResource(Resource resource);
    @Delete
    void deleteResource(Resource resource);
    @Update
    void updateResource(Resource resource);
    @Query("SELECT * FROM resource")
    LiveData<List<Resource>> findAllResources();

    @Transaction
    @Query("SELECT * FROM course")
    public List<CourseWithCourseItems> getCoursesWithCourseItems();

    @Transaction
    @Query("SELECT * FROM courseitem")
    public List<CourseItemWithResources> getCourseItemsWithResources();

    @Transaction
    @Query("SELECT * FROM quiz")
    public List<QuizWithQuestions> getQuizesWithQuestions();

    @Transaction
    @Query("SELECT * FROM courseitem")
    public List<CourseItemAndQuiz> getCourseItemsAndQuizes();

}
