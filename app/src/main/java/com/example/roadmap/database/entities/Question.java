package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey
    public int questionId;
    public String questionType;
    public String questionText;
    public String questionCorrectAnswer;
    public String questionPossibleAnswers;
    public int parentQuizId;
}
