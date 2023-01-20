package com.example.roadmap.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Course.class,
                CourseItem.class,
                Note.class,
                Quiz.class,
                Question.class,
                Resource.class,
                SavedCourse.class
        },
        version = 1,
        exportSchema = true)
public abstract class RoadmapDatabase extends RoomDatabase {
    private static RoadmapDatabase roadmapDb;
    static final ExecutorService dbWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract CourseDao courseDao();
    public abstract CourseItemDao courseItemDao();
    public abstract NoteDao noteDao();
    public abstract QuestionDao questionDao();
    public abstract QuizDao quizDao();
    public abstract ResourceDao resourceDao();
    public abstract SavedCourseDao savedCourseDao();

    static RoadmapDatabase getDatabase(Context context){
        if(roadmapDb == null){
            roadmapDb = Room.databaseBuilder(context.getApplicationContext(),
                    RoadmapDatabase.class, "roadmap_db")
                            .allowMainThreadQueries()
                            .createFromAsset("database/roadmap_db.db").build();
        }
        return roadmapDb;
    }

}
