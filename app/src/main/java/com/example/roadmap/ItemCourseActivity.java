package com.example.roadmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.CourseItemAndQuiz;

public class ItemCourseActivity extends AppCompatActivity {

    private TextView courseItemDescriptionTextView;
    private TextView courseItemNameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_course);

        Bundle bundle = getIntent().getExtras();

        int courseItemID = bundle.getInt("COURSE_ITEM_ID");


        RoadmapViewModel roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        CourseItemAndQuiz courseItemAndQuiz = roadmapViewModel.findConcreteCourseItemWithQuizes(courseItemID);

        courseItemNameTextView = findViewById(R.id.course_item_name);
        courseItemNameTextView.setText(courseItemAndQuiz.courseItem.courseItemName);

        courseItemDescriptionTextView = findViewById(R.id.course_item_description);
        courseItemDescriptionTextView.setText(courseItemAndQuiz.courseItem.courseItemDescription);


    }
}