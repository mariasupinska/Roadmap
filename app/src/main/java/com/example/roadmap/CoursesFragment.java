package com.example.roadmap;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.relations.CourseWithCourseItems;

import java.util.LinkedList;
import java.util.List;


public class CoursesFragment extends Fragment {

    private RoadmapViewModel roadmapViewModel;
    private RecyclerView recyclerViewRole;
    private RecyclerView recyclerViewSkill;

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
        private CourseWithCourseItems courseWithCourseItems;

        public CourseHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_course, parent, false));
            itemView.setOnClickListener(this);
            courseNameTextView = itemView.findViewById(R.id.course_name);
        }

        public void bind(CourseWithCourseItems courseWithCourseItems){
            this.courseWithCourseItems = courseWithCourseItems;
            courseNameTextView.setText(courseWithCourseItems.course.courseName);
        }

        @Override
        public void onClick(View view) {
            FragmentTransaction fragmentTransaction = getActivity().
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new CourseItemsFragment(courseWithCourseItems.course.courseName, courseWithCourseItems.courseItems));
            fragmentTransaction.commit();
        }

    }

    private class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
        private List<CourseWithCourseItems> courses;

        @NonNull
        @Override
        public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CourseHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
            if ( courses != null ) {
                CourseWithCourseItems course = courses.get(position);
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

        List<CourseWithCourseItems> separateCourses(List<CourseWithCourseItems> courses, String category){
            List<CourseWithCourseItems> tmpCourses = new LinkedList<>();
            for(int i = 0; i< courses.size(); i++){
                if(courses.get(i).course.courseCategory.equals(category))
                    tmpCourses.add(courses.get(i));
            }

            return tmpCourses;
        }

        void setSkillCourses(List<CourseWithCourseItems> courses){
            this.courses = separateCourses(courses, "Skill");
            notifyDataSetChanged();
        }

        void setRoleCourses(List<CourseWithCourseItems> courses) {
            this.courses = separateCourses(courses, "Role");
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
        final CourseAdapter adapterRole = new CourseAdapter();
        final CourseAdapter adapterSkill = new CourseAdapter();

        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerViewRole = view.findViewById(R.id.recycler_view);
        recyclerViewSkill = view.findViewById(R.id.skill_based_recycler);
        recyclerViewRole.setAdapter(adapterRole);
        recyclerViewSkill.setAdapter(adapterSkill);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewRole.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerViewSkill.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerViewRole.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            recyclerViewSkill.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        List<CourseWithCourseItems> courses = roadmapViewModel.findAllCoursesWithCourseItems();
        adapterRole.setRoleCourses(courses);
        adapterSkill.setSkillCourses(courses);

        return view;
    }
}