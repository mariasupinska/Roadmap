package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quiz {
    @PrimaryKey
    public int quizId;
    public int parentCourseItemId;
    public String quizDifficulty;

    public Quiz(int quizId, int parentCourseItemId, String quizDifficulty) {
        this.quizId = quizId;
        this.parentCourseItemId = parentCourseItemId;
        this.quizDifficulty = quizDifficulty;
    }
}
