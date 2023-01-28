package com.example.roadmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        Bundle bundle = getIntent().getExtras();
        int courseItemID = bundle.getInt("COURSE_ITEM_ID");
        int score = bundle.getInt("USER_SCORE");
        int amountOfQuestions = bundle.getInt("AMOUNT_OF_QUESTIONS");

        TextView finalScoreTextView = findViewById(R.id.final_score);
        Button okButton = findViewById(R.id.ok_button);

        finalScoreTextView.setText(getResources().getString(R.string.score, score, amountOfQuestions));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinalScoreActivity.this, ItemCourseActivity.class);
                i.putExtra("COURSE_ITEM_ID", courseItemID);
                startActivity(i);
            }
        });
    }
}