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
    public String parentCourseItemId;
}
