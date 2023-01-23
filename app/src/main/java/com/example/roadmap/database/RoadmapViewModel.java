package com.example.roadmap.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

public class RoadmapViewModel extends AndroidViewModel {
    private final RoadmapRepository roadmapRepository;
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

    public RoadmapViewModel(@NonNull Application application){
        super(application);
        roadmapRepository = new RoadmapRepository(application);
        courses = roadmapRepository.findAllCourses();
        notes = roadmapRepository.findAllNotes();
        courseItems = roadmapRepository.findAllCourseItems();
        questions = roadmapRepository.findAllQuestions();
        quizes = roadmapRepository.findAllQuizes();
        resources = roadmapRepository.findAllResources();
        savedCourses = roadmapRepository.findAllSavedCourses();
        courseItemsAndQuizes = roadmapRepository.findAllCourseItemsAndQuizes();
        coursesWithCourseItems = roadmapRepository.findAllCoursesWithCourseItems();
        favouriteCourseItems = roadmapRepository.getFavouriteCourseItems();
    }

    public LiveData<List<Course>> findAllCourses() {return courses;}
    public LiveData<List<CourseItem>> findAllCourseItems() {return courseItems;}
    public LiveData<List<Note>> findAllNotes() {return notes;}
    public LiveData<List<Question>> findAllQuestions() {return questions;}
    public LiveData<List<Quiz>> findAllQuizes() {return quizes;}
    public LiveData<List<Resource>> findAllResources() {return resources;}
    public LiveData<List<SavedCourse>> findAllSavedCourses() {return savedCourses;}

    public List<CourseItemAndQuiz> findAllCourseItemsAndQuizes() {return courseItemsAndQuizes;}
    public List<CourseWithCourseItems> findAllCoursesWithCourseItems() {return coursesWithCourseItems;}
    public List<CourseItem> getFavouriteCourseItems() {return favouriteCourseItems;}

    public int getResourcesAmount(int courseItemID){
        return roadmapRepository.getResourcesAmount(courseItemID);
    }

    public CourseItemWithResources findCourseItemWithResources(int courseItemID) {
        return roadmapRepository.findCourseItemWithResources(courseItemID);
    }

    public CourseItemAndQuiz findConcreteCourseItemWithQuizes(int courseItemId) {
        return roadmapRepository.findConcreteCourseItemWithQuizes(courseItemId);
    }
    public QuizWithQuestions findConcreteQuizWithQuestions(int quizId) {
        return roadmapRepository.findConcreteQuizWithQuestions(quizId);
    }

    public void insertNote(Note note){
        roadmapRepository.insertNote(note);
    }

    public void updateNote(Note note){
        roadmapRepository.updateNote(note);
    }

    public void deleteNote(Note note){
        roadmapRepository.deleteNote(note);
    }

    public void updateCourseItem(CourseItem courseItem){
        roadmapRepository.updateCourseItem(courseItem);
    }
}
