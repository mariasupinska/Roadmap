package com.example.roadmap;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.QuizWithQuestions;

abstract class QuizBuilder {
    public abstract void addQuestions(RoadmapViewModel viewModel, int quizId);
    public abstract QuizWithQuestions getQuizWithQuestions();

}
