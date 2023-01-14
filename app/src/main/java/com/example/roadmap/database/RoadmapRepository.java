package com.example.roadmap.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

public class RoadmapRepository {
    private final RoadmapDao roadmapDao;
    private final LiveData<List<Course>> courses;
    private final LiveData<List<CourseItem>> courseItems;
    private final LiveData<List<Note>> notes;
    private final LiveData<List<Question>> questions;
    private final LiveData<List<Quiz>> quizes;
    private final LiveData<List<Resource>> resources;

    private final List<CourseItemAndQuiz> courseItemsAndQuizes;
    private final List<CourseItemWithResources> courseItemsWithResources;
    private final List<CourseWithCourseItems> coursesWithCourseItems;
    private final List<QuizWithQuestions> quizesWithQuestions;

    RoadmapRepository(Application application){
        RoadmapDatabase db = RoadmapDatabase.getDatabase(application);
        roadmapDao = db.roadmapDao();
        courses = roadmapDao.findAllCourses();
        notes = roadmapDao.findAllNotes();
        courseItems = roadmapDao.findAllCourseItems();
        questions = roadmapDao.findAllQuestions();
        quizes = roadmapDao.findAllQuizes();
        resources = roadmapDao.findAllResources();
        courseItemsAndQuizes = roadmapDao.getCourseItemsAndQuizes();
        courseItemsWithResources = roadmapDao.getCourseItemsWithResources();
        coursesWithCourseItems = roadmapDao.getCoursesWithCourseItems();
        quizesWithQuestions = roadmapDao.getQuizesWithQuestions();
    }

    LiveData<List<Course>> findAllCourses() {return courses;}
    LiveData<List<CourseItem>> findAllCourseItems() {return courseItems;}
    LiveData<List<Note>> findAllNotes() {return notes;}
    LiveData<List<Question>> findAllQuestions() {return questions;}
    LiveData<List<Quiz>> findAllQuizes() {return quizes;}
    LiveData<List<Resource>> findAllResources() {return resources;}

    List<CourseItemAndQuiz> findAllCourseItemsAndQuizes() {return courseItemsAndQuizes;}
    List<CourseItemWithResources> findAllCourseItemsWithResources() {return courseItemsWithResources;}
    List<CourseWithCourseItems> findAllCoursesWithCourseItems() {return coursesWithCourseItems;}
    List<QuizWithQuestions> findAllQuizesWithQuestions() {return quizesWithQuestions;}

    void insertNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> roadmapDao.insertNote(note));
    }

    void updateNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> roadmapDao.updateNote(note));
    }

    void deleteNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> roadmapDao.deleteNote(note));
    }
}