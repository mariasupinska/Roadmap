package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.roadmap.database.entities.Quiz;
import com.example.roadmap.database.relations.QuizWithQuestions;

import java.util.List;

@Dao
public interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuiz(Quiz quiz);

    @Delete
    void deleteQuiz(Quiz quiz);

    @Update
    void updateQuiz(Quiz quiz);

    @Query("SELECT * FROM quiz")
    LiveData<List<Quiz>> findAllQuizes();

    @Transaction
    @Query("SELECT * FROM quiz WHERE quizId = :quizID")
    public QuizWithQuestions getQuizesWithQuestions(int quizID);
}
