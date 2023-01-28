package com.example.roadmap;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.Resource;
import com.example.roadmap.database.relations.CourseItemAndQuiz;

import java.util.List;

public class ItemCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView courseItemDescriptionTextView;
    private TextView courseItemNameTextView;

    private TextView noQuizTextView;
    private TextView resourceTextView;

    private Button favouriteButton;

    private Button practise_quiz;
    private Button marked_quiz;
    private Button start_quiz;

    private State state;
    private int quizId;

    private void changeState(State state) {
        this.state = state;
    }

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

        if (courseItemAndQuiz.quiz != null) {
            quizId = courseItemAndQuiz.quiz.quizId;
        }

        courseItemNameTextView = findViewById(R.id.course_item_name);
        courseItemNameTextView.setText(courseItemAndQuiz.courseItem.courseItemName);

        courseItemDescriptionTextView = findViewById(R.id.course_item_description);
        courseItemDescriptionTextView.setText(courseItemAndQuiz.courseItem.courseItemDescription);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        adapter.setResources(roadmapViewModel.findCourseItemWithResources(courseItemID).resources);

        favouriteButton = findViewById(R.id.favourite_button);

        if (courseItemAndQuiz.courseItem.isFavourite == 0){
            favouriteButton.setText("Add to favourites");
        } else {
            favouriteButton.setText("Remove from favourites");
        }

        favouriteButton.setOnClickListener(view -> {
            if (courseItemAndQuiz.courseItem.isFavourite == 0){
                courseItemAndQuiz.courseItem.isFavourite = 1;
                favouriteButton.setText("Remove from favourites");
            } else {
                courseItemAndQuiz.courseItem.isFavourite = 0;
                favouriteButton.setText("Add to favourites");
            }
            roadmapViewModel.updateCourseItem(courseItemAndQuiz.courseItem);
        });

        practise_quiz = findViewById(R.id.practise_quiz_button);
        marked_quiz = findViewById(R.id.marked_quiz_button);
        start_quiz = findViewById(R.id.start_quiz_button);

        if (courseItemAndQuiz.quiz == null) {
            practise_quiz.setVisibility(View.GONE);
            marked_quiz.setVisibility(View.GONE);
            start_quiz.setVisibility(View.GONE);
            noQuizTextView = findViewById(R.id.no_quiz_text);
            noQuizTextView.setVisibility(View.VISIBLE);

        } else {
            practise_quiz.setBackgroundColor(Color.GRAY);
            marked_quiz.setBackgroundColor(Color.GRAY);
            start_quiz.setVisibility(View.GONE);
        }

        practise_quiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                changeState(new PractiseState(ItemCourseActivity.this));
                state.loadPractiseQuiz();
                practise_quiz.setBackgroundColor(getResources().getColor(R.color.purple_500));
                marked_quiz.setBackgroundColor(Color.GRAY);
                start_quiz.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Practise option chosen!", Toast.LENGTH_SHORT).show();
            }
        });

        marked_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(new MarkedState(ItemCourseActivity.this));
                state.loadMarkedQuiz();
                marked_quiz.setBackgroundColor(getResources().getColor(R.color.purple_500));
                practise_quiz.setBackgroundColor(Color.GRAY);
                start_quiz.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Marked option chosen!", Toast.LENGTH_SHORT).show();
            }
        });

        start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemCourseActivity.this, QuizActivity.class);
                i.putExtra("COURSE_ITEM_ID", courseItemID);
                i.putExtra("IF_MARKED", state.isMarked());
                i.putExtra("QUIZ_ID", quizId);
                startActivity(i);
            }
        });
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