package com.example.roadmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.CourseItemAndQuiz;

public class ItemCourseActivity extends AppCompatActivity {

    private TextView parentcoursetextview;
    private TextView courseitemtexview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_course);

        Bundle bundle = getIntent().getExtras();

        String parentCourseID;
        String courseItem;
        int courseItemID = bundle.getInt("COURSE_ITEM_ID");

        if (bundle != null) {
            parentCourseID = "Parent Course ID: " + bundle.getInt("PARENT_COURSE_ID");
            courseItem =  "Course Item ID: " + bundle.getInt("COURSE_ITEM_ID");
            courseItemID = bundle.getInt("COURSE_ITEM_ID");
        } else {
            parentCourseID = "No parent course ID";
            courseItem = "No course item ID";
        }

        RoadmapViewModel roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        CourseItemAndQuiz courseItemAndQuiz = roadmapViewModel.findConcreteCourseItemWithQuizes(courseItemID);

        courseitemtexview = findViewById(R.id.textView);
        courseitemtexview.setText(courseItemAndQuiz.courseItem.courseItemName);


    }
}