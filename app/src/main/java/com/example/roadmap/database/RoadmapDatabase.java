package com.example.roadmap.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Note;
import com.example.roadmap.database.entities.Question;
import com.example.roadmap.database.entities.Quiz;
import com.example.roadmap.database.entities.Resource;
import com.example.roadmap.database.entities.SavedCourses;

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
                SavedCourses.class
        },
        version = 1,
        exportSchema = true)
public abstract class RoadmapDatabase extends RoomDatabase {
    private static RoadmapDatabase roadmapDb;
    static final ExecutorService dbWriteExecutor = Executors.newSingleThreadExecutor();

    public  abstract RoadmapDao roadmapDao();

    static RoadmapDatabase getDatabase(Context context){
        if(roadmapDb == null){
            roadmapDb = Room.databaseBuilder(context.getApplicationContext(),
                    RoadmapDatabase.class, "roadmap_db").addCallback(roomDatabaseCallback)
                            .allowMainThreadQueries()
                            .createFromAsset("database/roadmap_db.db").build();
        }
        return roadmapDb;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            dbWriteExecutor.execute(() -> {
                RoadmapDao dao = roadmapDb.roadmapDao();

                dao.insertNote(new Note(1, "pierwsza notatka", "pierwsza notatka"));
                dao.insertNote(new Note(2, "druga notatka", "druga notatka"));
                dao.insertNote(new Note(3, "trzecia notatka", "trzecia notatka"));
            });

        }
    };
}
