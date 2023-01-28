package com.example.roadmap;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.roadmap.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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
                    replaceFragment(new SavedCourseItemsFragment());
                    return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (binding.bottomNavigationView.getSelectedItemId() == R.id.nav_courses) {
            transaction.replace(R.id.frame_layout, new CoursesFragment());
            transaction.commit();
        }
        else if(binding.bottomNavigationView.getSelectedItemId() == R.id.nav_notes){
            transaction.replace(R.id.frame_layout, new NotesFragment());
            transaction.commit();
        }
        else {
            binding.bottomNavigationView.setSelectedItemId(R.id.nav_courses);
        }
    }
}