package com.example.roadmap;

import com.example.roadmap.database.RoadmapViewModel;
import com.example.roadmap.database.entities.CourseItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class CourseItemData {

    private List<CourseItem> courseItems;
    private RoadmapViewModel roadmapViewModel;

    CourseItemData(List<CourseItem> courseItems, RoadmapViewModel roadmapViewModel){
        this.courseItems = courseItems;
        this.roadmapViewModel = roadmapViewModel;
    }

    public Iterator<CourseItem> resourcesIterator() {
        return new Iterator<CourseItem>(){
            List<CourseItem> tmpItems = new LinkedList<>(courseItems);
            CourseItem courseItem;
            int resourceCount = 0;
            int toDelete;
            int count;
            public boolean hasNext() {
                if(tmpItems.size() == 0)
                    return false;
                return true;
            }

            @Override
            public CourseItem next() {
                for (int i = 0; i<tmpItems.size(); i++){
                    count = roadmapViewModel.getResourcesAmount(tmpItems.get(i).courseItemId);
                    if(count > resourceCount){
                        courseItem = tmpItems.get(i);
                        resourceCount = count;
                        toDelete = i;
                    }
                }
                resourceCount = 0;
                tmpItems.remove(toDelete);
                return courseItem;
            }
        };
    }

    public Iterator<CourseItem> searchIterator(String query) {
        String lowerCaseQuery = query.toLowerCase(Locale.ROOT);
        return new Iterator<CourseItem>() {
            int position = 0;
            @Override
            public boolean hasNext() {
                if(position == courseItems.size())
                    return false;
                return true;
            }

            @Override
            public CourseItem next() {
                if(courseItems.get(position).courseItemName.toLowerCase(Locale.ROOT).contains(lowerCaseQuery))
                {
                    return courseItems.get(position++);
                }
                position++;
                return null;
            }
        };
    }

}
