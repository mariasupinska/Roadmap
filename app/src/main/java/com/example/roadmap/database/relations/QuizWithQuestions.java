package com.example.roadmap.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.roadmap.database.entities.Question;
import com.example.roadmap.database.entities.Quiz;

import java.util.List;

//one to many relationship
public class QuizWithQuestions {
    @Embedded
    public Quiz quiz;

    @Relation(
            parentColumn = "quizId",
            entityColumn = "parentQuizId"
    )
    public List<Question> questions;
}
