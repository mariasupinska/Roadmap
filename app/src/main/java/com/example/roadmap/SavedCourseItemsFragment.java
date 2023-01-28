package com.example.roadmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.CourseItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SavedCourseItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CourseItem> courseItems;
    private RoadmapViewModel roadmapViewModel;
    private CourseItemData courseItemData;
    private SavedCourseItemsFragment.SavedCourseItemsAdapter adapter;
    private Iterator<CourseItem> iterator;

    public SavedCourseItemsFragment() {
    }

    private class SavedCourseItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView;
        private CourseItem courseItem;
        private TextView resourceCount;

        public SavedCourseItemsHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.single_course_item, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.course_item_name);
            resourceCount = itemView.findViewById(R.id.resource_count);

        }

        public void bind(CourseItem courseItem){
            this.courseItem = courseItem;
            titleTextView.setText(courseItem.courseItemName);
            resourceCount.setText(getResources().getString(R.string.resources,
                    roadmapViewModel.getResourcesAmount(courseItem.courseItemId)));
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(), ItemCourseActivity.class);
            i.putExtra("COURSE_ITEM_ID", this.courseItem.courseItemId);
            startActivity(i);
        }

    }

    private class SavedCourseItemsAdapter extends RecyclerView.Adapter<SavedCourseItemsFragment.SavedCourseItemsHolder> {
        private List<CourseItem> courseItems;

        @NonNull
        @Override
        public SavedCourseItemsFragment.SavedCourseItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SavedCourseItemsFragment.SavedCourseItemsHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SavedCourseItemsFragment.SavedCourseItemsHolder holder, int position) {
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
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_courses, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new SavedCourseItemsFragment.SavedCourseItemsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);

        SavedCourseItemsStorage savedCourseItemsStorage = SavedCourseItemsStorage.getInstance();

        savedCourseItemsStorage.init(roadmapViewModel);

        courseItems = savedCourseItemsStorage.getCourseItems();
        courseItemData = new CourseItemData(courseItems, roadmapViewModel);

        adapter.setCourseItems(courseItems);

        return view;
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.course_item_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            List<CourseItem> searchedCourseItems = new LinkedList<>();

            @Override
            public boolean onQueryTextSubmit(String s) {
                iterator = courseItemData.searchIterator(s);
                CourseItem nextItem;
                while(iterator.hasNext()){
                    nextItem = iterator.next();
                    if(nextItem != null){
                        searchedCourseItems.add(nextItem);
                    }
                }
                adapter.setCourseItems(searchedCourseItems);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                iterator = courseItemData.searchIterator(s);
                CourseItem nextItem;
                while(iterator.hasNext()){
                    nextItem = iterator.next();
                    if(nextItem != null){
                        searchedCourseItems.add(nextItem);
                    }
                }
                adapter.setCourseItems(searchedCourseItems);
                searchedCourseItems = new LinkedList<>();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sort_by_resources:
                List<CourseItem> sortedCourseItems = new LinkedList<>();
                iterator = courseItemData.resourcesIterator();

                while(iterator.hasNext()){
                    sortedCourseItems.add(iterator.next());
                }

                adapter.setCourseItems(sortedCourseItems);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

}
