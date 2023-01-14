package com.example.roadmap.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Resource {
    @PrimaryKey
    public int resourceId;
    public String resourceType;
    public String resourceUrl;
    public String resourceName;
    public int parentCourseItemId;

    public Resource(int resourceId, String resourceType, String resourceUrl, String resourceName, int parentCourseItemId) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.resourceUrl = resourceUrl;
        this.resourceName = resourceName;
        this.parentCourseItemId = parentCourseItemId;
    }
}
