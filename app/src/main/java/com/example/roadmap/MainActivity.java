package com.example.roadmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.roadmap.databinding.ActivityMainBinding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Course;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoadmapViewModel roadmapViewModel;
    private TextView textView;
    private LiveData<List<Course>> courses;

    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new CoursesFragment());
        
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_courses:
                    replaceFragment(new CoursesFragment());
                    return true;
                case R.id.nav_notes:
                    replaceFragment(new NotesFragment());
                    return true;
                case R.id.nav_saved_courses:
                    replaceFragment(new SavedCoursesFragment());
                    return true;
            }
            return false;
        });

        //textView = findViewById(R.id.courses_text_view);
        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        courses = roadmapViewModel.findAllCourses();
        //courses.observe(this, courses -> textView.setText(courses.get(0).courseName));


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}