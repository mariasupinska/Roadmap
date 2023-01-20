package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roadmap.database.entities.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);

    @Delete
    void deleteQuestion(Question question);

    @Update
    void updateQuestion(Question question);

    @Query("SELECT * FROM question")
    LiveData<List<Question>> findAllQuestions();
}
