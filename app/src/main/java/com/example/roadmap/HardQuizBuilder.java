package com.example.roadmap;

import android.os.Build;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.QuizWithQuestions;

public class HardQuizBuilder extends QuizBuilder{
    protected QuizWithQuestions quizWithQuestions;


    @Override
    public void addQuestions(RoadmapViewModel viewModel, int quizId) {
        quizWithQuestions = viewModel.findConcreteQuizWithQuestions(quizId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            quizWithQuestions.questions.removeIf(question -> !question.getQuestionType().equals("abcd"));
        }

    }
    @Override
    public QuizWithQuestions getQuizWithQuestions() {
        return quizWithQuestions;
    }

}
