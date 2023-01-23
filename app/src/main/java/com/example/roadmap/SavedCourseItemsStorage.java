package com.example.roadmap;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.CourseItem;

import java.text.Collator;
import java.util.List;

public class SavedCourseItemsStorage {
    private List<CourseItem> courseItems;
    private static RoadmapViewModel roadmapViewModel;
    private static SavedCourseItemsStorage savedCourseItemsStorage;
    private SavedCourseItemsStorage() {

    }
    public void init(RoadmapViewModel roadmapViewModel) {
        this.roadmapViewModel = roadmapViewModel;
        courseItems = roadmapViewModel.getFavouriteCourseItems();
    }

    public static SavedCourseItemsStorage getInstance() {
        if (savedCourseItemsStorage == null) {
            savedCourseItemsStorage = new SavedCourseItemsStorage();
        }
        return savedCourseItemsStorage;
    }

    public List<CourseItem> getCourseItems() {
        return courseItems;
    }

}
