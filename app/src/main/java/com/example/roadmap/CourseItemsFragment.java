package com.example.roadmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.CourseItem;
import com.example.roadmap.database.entities.Note;
import com.example.roadmap.database.relations.CourseItemWithResources;
import com.example.roadmap.database.relations.CourseWithCourseItems;

import java.util.List;

public class CourseItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CourseItem> courseItems;


    public CourseItemsFragment() {
    }

    public CourseItemsFragment(List<CourseItem> courseItems) {
        this.courseItems = courseItems;
    }

    private class CourseItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private CourseItem courseItem;

        public CourseItemsHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_course_item, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.course_item_name);

        }

        public void bind(CourseItem courseItem){
            this.courseItem = courseItem;
            titleTextView.setText(courseItem.courseItemName);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(), ItemCourseActivity.class);
            i.putExtra("PARENT_COURSE_ID", this.courseItem.parentCourseId);
            i.putExtra("COURSE_ITEM_ID", this.courseItem.courseItemId);
            startActivity(i);
        }

    }

    private class CourseItemsAdapter extends RecyclerView.Adapter<CourseItemsFragment.CourseItemsHolder> {
        private List<CourseItem> courseItems;

        @NonNull
        @Override
        public CourseItemsFragment.CourseItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CourseItemsFragment.CourseItemsHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseItemsFragment.CourseItemsHolder holder, int position) {
            if ( courseItems != null ) {
                CourseItem courseItem = courseItems.get(position);
                holder.bind(courseItem);
            } else {
                Log.d("MainActivity", "No courses");
            }
        }

        @Override
        public int getItemCount() {
            if ( courseItems != null ) {
                return courseItems.size();
            } else {
                return 0;
            }
        }

        void setCourseItems(List<CourseItem> courseItems) {
            this.courseItems = courseItems;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_items, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        final CourseItemsFragment.CourseItemsAdapter adapter = new CourseItemsFragment.CourseItemsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
//        roadmapViewModel.findAllNotes().observe(getViewLifecycleOwner(), adapter::setNotes);

        adapter.setCourseItems(courseItems);

        return view;
    }
}
