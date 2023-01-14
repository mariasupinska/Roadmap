package com.example.roadmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Course;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoadmapViewModel roadmapViewModel;
    private TextView textView;
    private LiveData<List<Course>> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);


        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        courses = roadmapViewModel.findAllCourses();
        courses.observe(this, courses -> textView.setText(courses.get(0).courseName));
    }
}