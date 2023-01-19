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

    private final List<CourseItemAndQuiz> courseItemsAndQuizes;
    private final List<CourseItemWithResources> courseItemsWithResources;
    private final List<CourseWithCourseItems> coursesWithCourseItems;
    private final List<QuizWithQuestions> quizesWithQuestions;

    public RoadmapViewModel(@NonNull Application application){
        super(application);
        roadmapRepository = new RoadmapRepository(application);
        courses = roadmapRepository.findAllCourses();
        notes = roadmapRepository.findAllNotes();
        courseItems = roadmapRepository.findAllCourseItems();
        questions = roadmapRepository.findAllQuestions();
        quizes = roadmapRepository.findAllQuizes();
        resources = roadmapRepository.findAllResources();
        courseItemsAndQuizes = roadmapRepository.findAllCourseItemsAndQuizes();
        courseItemsWithResources = roadmapRepository.findAllCourseItemsWithResources();
        coursesWithCourseItems = roadmapRepository.findAllCoursesWithCourseItems();
        quizesWithQuestions = roadmapRepository.findAllQuizesWithQuestions();
    }

    public LiveData<List<Course>> findAllCourses() {return courses;}
    public LiveData<List<CourseItem>> findAllCourseItems() {return courseItems;}
    public LiveData<List<Note>> findAllNotes() {return notes;}
    public LiveData<List<Question>> findAllQuestions() {return questions;}
    public LiveData<List<Quiz>> findAllQuizes() {return quizes;}
    public LiveData<List<Resource>> findAllResources() {return resources;}

    public List<CourseItemAndQuiz> findAllCourseItemsAndQuizes() {return courseItemsAndQuizes;}
    public List<CourseItemWithResources> findAllCourseItemsWithResources() {return courseItemsWithResources;}
    public List<CourseWithCourseItems> findAllCoursesWithCourseItems() {return coursesWithCourseItems;}
    public List<QuizWithQuestions> findAllQuizesWithQuestions() {return quizesWithQuestions;}

    public void insertNote(Note note){
        roadmapRepository.insertNote(note);
    }

    public void updateNote(Note note){
        roadmapRepository.updateNote(note);
    }

    public void deleteNote(Note note){
        roadmapRepository.deleteNote(note);
    }
}
