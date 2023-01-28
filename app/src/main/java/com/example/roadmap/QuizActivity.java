package com.example.roadmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Question;
import com.example.roadmap.database.relations.QuizWithQuestions;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private Button answer_1;
    private Button answer_2;
    private Button answer_3;
    private Button answer_4;
    private String[] answers;

    int practiseAmount = 5;

    int currentIndex = 0;
    int finalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle bundle = getIntent().getExtras();
        int courseItemID = bundle.getInt("COURSE_ITEM_ID");
        int quizId = bundle.getInt("QUIZ_ID");
        boolean isMarked = bundle.getBoolean("IF_MARKED");

        RoadmapViewModel roadmapViewModel;
        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        QuizWithQuestions quizWithQuestions = roadmapViewModel.findConcreteQuizWithQuestions(quizId);

        questionTextView = findViewById(R.id.question_text_view);
        answer_1 = findViewById(R.id.answer_one);
        answer_2 = findViewById(R.id.answer_two);
        answer_3 = findViewById(R.id.answer_three);
        answer_4 = findViewById(R.id.answer_four);

        List<Question> questions = new LinkedList<>(quizWithQuestions.questions);
        List<Question> questionsForUser = new LinkedList<>();

        for ( int i = 0; i < practiseAmount; i++ ) {
            int index = new Random().nextInt(questions.size());
            questionsForUser.add(questions.get(index));
            questions.remove(index);
        }

        setNextQuestion(questionsForUser);


        answer_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler(isMarked, questionsForUser, 0, courseItemID);
            }
        });

        answer_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler(isMarked, questionsForUser, 1, courseItemID);
            }
        });

        answer_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler(isMarked, questionsForUser, 2, courseItemID);
            }
        });

        answer_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler(isMarked, questionsForUser, 3, courseItemID);
            }
        });

    }

    private void onClickHandler(boolean isMarked, List<Question> questionsForUser, int index, int courseItemID) {
        if (isMarked) {
            checkAnswerCorrectness(questionsForUser, index);
        }

        currentIndex++;

        if ( currentIndex < questionsForUser.size() ) {
            setNextQuestion(questionsForUser);
        } else {
            Intent intent;
            if (isMarked) {
                intent = new Intent(getApplicationContext(), FinalScoreActivity.class);
                intent.putExtra("COURSE_ITEM_ID", courseItemID);
                intent.putExtra("USER_SCORE", finalScore);
                intent.putExtra("AMOUNT_OF_QUESTIONS", questionsForUser.size());
            } else {
                intent = new Intent(getApplicationContext(), ItemCourseActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }

    private void checkAnswerCorrectness(List<Question> questionsForUser, int index) {
        if ( answers[index].equals(questionsForUser.get(currentIndex).questionCorrectAnswer) ) {
            finalScore++;
        }
    }

    private void setNextQuestion(List<Question> questionsForUser) {
        String possibleAnswers = questionsForUser.get(currentIndex).questionPossibleAnswers;
        answers = possibleAnswers.split(",");

        questionTextView.setText(questionsForUser.get(currentIndex).questionText);

        if (answers.length == 4) {
            answer_1.setText(answers[0]);
            answer_2.setText(answers[1]);
            answer_3.setVisibility(View.VISIBLE);
            answer_4.setVisibility(View.VISIBLE);
            answer_3.setText(answers[2]);
            answer_4.setText(answers[3]);

        } else {
            answer_1.setText(answers[0]);
            answer_2.setText(answers[1]);
            answer_3.setVisibility(View.GONE);
            answer_4.setVisibility(View.GONE);
        }
    }

}