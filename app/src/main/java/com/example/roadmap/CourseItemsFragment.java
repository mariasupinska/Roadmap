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

import org.w3c.dom.Text;

import java.util.List;

public class CourseItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CourseItem> courseItems;
    private String parentCourseName;
    private RoadmapViewModel roadmapViewModel;

    public CourseItemsFragment() {
    }

    public CourseItemsFragment(String parentCourseName, List<CourseItem> courseItems) {
        this.courseItems = courseItems;
        this.parentCourseName = parentCourseName;
    }

    private class CourseItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView parentCourseTextView;
        private TextView titleTextView;
        private CourseItem courseItem;
        private TextView resourceCount;

        public CourseItemsHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_course_item, parent, false));
            itemView.setOnClickListener(this);

            parentCourseTextView = itemView.findViewById(R.id.parent_course_header);
            titleTextView = itemView.findViewById(R.id.course_item_name);
            resourceCount = itemView.findViewById(R.id.resource_count);

        }

        public void bind(CourseItem courseItem){
            this.courseItem = courseItem;
            titleTextView.setText(courseItem.courseItemName);

            resourceCount.setText(resourceCount.getText()+
                    String.valueOf(roadmapViewModel.getResourcesAmount(courseItem.courseItemId)));
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(), ItemCourseActivity.class);
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

        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        TextView parentCourseHeader;
        parentCourseHeader = view.findViewById(R.id.parent_course_header);
        parentCourseHeader.setText(parentCourseName);

        adapter.setCourseItems(courseItems);

        return view;
    }
}
