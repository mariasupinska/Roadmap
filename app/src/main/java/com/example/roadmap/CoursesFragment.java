package com.example.roadmap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Course;
import com.example.roadmap.database.entities.Note;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment {

    private RoadmapViewModel roadmapViewModel;
    private RecyclerView recyclerView;

    public CoursesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(String param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView courseNameTextView;
        private Course course;

        public CourseHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_course, parent, false));
            itemView.setOnClickListener(this);
            courseNameTextView = itemView.findViewById(R.id.course_name);
        }

        public void bind(Course course){
            this.course = course;
            courseNameTextView.setText(course.courseName);
            System.out.printf("");
        }

        @Override
        public void onClick(View view) {
        }

    }

    private class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
        private List<Course> courses;

        @NonNull
        @Override
        public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CourseHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
            if ( courses != null ) {
                Course course = courses.get(position);
                holder.bind(course);
            } else {
                Log.d("MainActivity", "No courses");
            }
        }

        @Override
        public int getItemCount() {
            if ( courses != null ) {
                return courses.size();
            } else {
                return 0;
            }
        }

        void setCourses(List<Course> courses) {
            this.courses = courses;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final CourseAdapter adapter = new CourseAdapter();

        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        roadmapViewModel.findAllCourses().observe(getViewLifecycleOwner(), adapter::setCourses);

        return view;
    }
}