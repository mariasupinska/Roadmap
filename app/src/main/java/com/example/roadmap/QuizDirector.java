package com.example.roadmap;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.QuizWithQuestions;

public class QuizDirector {
    private QuizBuilder quizBuilder;

    public void setQuizBuilder(QuizBuilder qb) {
        quizBuilder = qb;
    }

    public QuizWithQuestions getQuizWithQuestions() {
        return quizBuilder.getQuizWithQuestions();
    }

    public void constructQuiz(RoadmapViewModel viewModel, int quizId, int difficulty) {
        if (difficulty == 1) {
            quizBuilder = new EasyQuizBuilder();
        } else if (difficulty == 2) {
            quizBuilder = new HardQuizBuilder();
        }
        quizBuilder.addQuestions(viewModel, quizId);

    }
}
