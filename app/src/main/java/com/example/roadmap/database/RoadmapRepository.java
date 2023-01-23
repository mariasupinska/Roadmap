package com.example.roadmap.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roadmap.database.dao.CourseDao;
import com.example.roadmap.database.dao.CourseItemDao;
import com.example.roadmap.database.dao.NoteDao;
import com.example.roadmap.database.dao.QuestionDao;
import com.example.roadmap.database.dao.QuizDao;
import com.example.roadmap.database.dao.ResourceDao;
import com.example.roadmap.database.dao.SavedCourseDao;
import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Note;
import com.example.roadmap.database.entities.Question;
import com.example.roadmap.database.entities.Quiz;
import com.example.roadmap.database.entities.Resource;
import com.example.roadmap.database.entities.SavedCourse;
import com.example.roadmap.database.relations.CourseItemAndQuiz;
import com.example.roadmap.database.relations.CourseItemWithResources;
import com.example.roadmap.database.relations.CourseWithCourseItems;
import com.example.roadmap.database.relations.QuizWithQuestions;

import java.util.List;

public class RoadmapRepository {

    private final CourseDao courseDao;
    private final CourseItemDao courseItemDao;
    private final NoteDao noteDao;
    private final QuestionDao questionDao;
    private final QuizDao quizDao;
    private final ResourceDao resourceDao;
    private final SavedCourseDao savedCourseDao;

    private final LiveData<List<Course>> courses;
    private final LiveData<List<CourseItem>> courseItems;
    private final LiveData<List<Note>> notes;
    private final LiveData<List<Question>> questions;
    private final LiveData<List<Quiz>> quizes;
    private final LiveData<List<Resource>> resources;
    private final LiveData<List<SavedCourse>> savedCourses;

    private final List<CourseItemAndQuiz> courseItemsAndQuizes;
    private final List<CourseWithCourseItems> coursesWithCourseItems;

    private final List<CourseItem> favouriteCourseItems;

    RoadmapRepository(Application application){
        RoadmapDatabase db = RoadmapDatabase.getDatabase(application);

        courseDao = db.courseDao();
        courseItemDao = db.courseItemDao();
        noteDao = db.noteDao();
        questionDao = db.questionDao();
        quizDao = db.quizDao();
        resourceDao = db.resourceDao();
        savedCourseDao = db.savedCourseDao();

        courses = courseDao.findAllCourses();
        courseItems = courseItemDao.findAllCourseItems();
        notes = noteDao.findAllNotes();
        questions = questionDao.findAllQuestions();
        quizes = quizDao.findAllQuizes();
        resources = resourceDao.findAllResources();
        savedCourses = savedCourseDao.findAllSavedCourses();

        courseItemsAndQuizes = courseItemDao.getCourseItemsAndQuizes();
        coursesWithCourseItems = courseDao.getCoursesWithCourseItems();

        favouriteCourseItems = courseItemDao.getFavouriteCourseItems();
    }

    LiveData<List<Course>> findAllCourses() {return courses;}
    LiveData<List<CourseItem>> findAllCourseItems() {return courseItems;}
    LiveData<List<Note>> findAllNotes() {return notes;}
    LiveData<List<Question>> findAllQuestions() {return questions;}
    LiveData<List<Quiz>> findAllQuizes() {return quizes;}
    LiveData<List<Resource>> findAllResources() {return resources;}
    LiveData<List<SavedCourse>> findAllSavedCourses() {return savedCourses;}

    List<CourseItemAndQuiz> findAllCourseItemsAndQuizes() {return courseItemsAndQuizes;}
    List<CourseWithCourseItems> findAllCoursesWithCourseItems() {return coursesWithCourseItems;}

    List<CourseItem> getFavouriteCourseItems() {return favouriteCourseItems;}
    public int getResourcesAmount(int courseItemID){
        return courseItemDao.getResourcesAmount(courseItemID);
    }

    public CourseItemWithResources findCourseItemWithResources(int courseItemID) {
        return courseItemDao.getCourseItemWithResources(courseItemID);
    }

    public CourseItemAndQuiz findConcreteCourseItemWithQuizes(int courseItemID) {
        return courseItemDao.getCourseItemWithQuizByCourseItemId(courseItemID);
    }

    public QuizWithQuestions findConcreteQuizWithQuestions(int quizID) {
        return quizDao.getQuizesWithQuestions(quizID);
    }
    void insertNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> noteDao.insertNote(note));
    }

    void updateNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> noteDao.updateNote(note));
    }

    void deleteNote(Note note){
        RoadmapDatabase.dbWriteExecutor.execute(() -> noteDao.deleteNote(note));
    }

    void updateCourseItem(CourseItem courseItem){
        RoadmapDatabase.dbWriteExecutor.execute(() -> courseItemDao.updateCourseItem(courseItem));
    }
}