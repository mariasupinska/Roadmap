package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quiz {
    @PrimaryKey
    public int quizId;
    public int parentCourseItemId;

    public Quiz(int quizId, int parentCourseItemId) {
        this.quizId = quizId;
        this.parentCourseItemId = parentCourseItemId;
    }
}
