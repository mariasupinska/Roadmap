package com.example.roadmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Resource;
import com.example.roadmap.database.relations.CourseItemAndQuiz;

import java.util.List;

public class ItemCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView courseItemDescriptionTextView;
    private TextView courseItemNameTextView;

    private TextView resourceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_course);

        final ItemCourseAdapter adapter = new ItemCourseAdapter();
        Bundle bundle = getIntent().getExtras();
        int courseItemID = bundle.getInt("COURSE_ITEM_ID");

        RoadmapViewModel roadmapViewModel;
        roadmapViewModel = new ViewModelProvider(this).get(RoadmapViewModel.class);
        CourseItemAndQuiz courseItemAndQuiz = roadmapViewModel.findConcreteCourseItemWithQuizes(courseItemID);

        courseItemNameTextView = findViewById(R.id.course_item_name);
        courseItemNameTextView.setText(courseItemAndQuiz.courseItem.courseItemName);

        courseItemDescriptionTextView = findViewById(R.id.course_item_description);
        courseItemDescriptionTextView.setText(courseItemAndQuiz.courseItem.courseItemDescription);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        adapter.setResources(roadmapViewModel.findCourseItemWithResources(courseItemID).resources);
    }

    private class ItemCourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Resource resource;
        public ItemCourseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.single_resource, parent, false));
            itemView.setOnClickListener(this);
            resourceTextView = itemView.findViewById(R.id.resource_item_title);
        }

        @Override
        public void onClick(View view) {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(resource.resourceUrl));
            startActivity(viewIntent);
        }

        public void bind(Resource resource){
            this.resource = resource;
            resourceTextView.setText(resource.resourceName);
            System.out.println(resource.resourceName);
        }
    }

    private class ItemCourseAdapter extends RecyclerView.Adapter<ItemCourseHolder>{

        private List<Resource> resources;

        @NonNull
        @Override
        public ItemCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemCourseHolder(LayoutInflater.from(parent.getContext()), parent);
        }


        @Override
        public void onBindViewHolder(@NonNull ItemCourseHolder holder, int position) {
            if (resources != null){
                holder.bind(resources.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (resources != null){
                return resources.size();
            }
            return 0;
        }

        public void setResources(List<Resource> resources){
            this.resources = resources;
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.api_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_books_api:
                startActivity(new Intent(ItemCourseActivity.this, RecommendedBooksActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}