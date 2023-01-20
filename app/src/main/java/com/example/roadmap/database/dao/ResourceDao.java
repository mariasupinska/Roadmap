package com.example.roadmap.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roadmap.database.entities.Resource;

import java.util.List;

@Dao
public interface ResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResource(Resource resource);

    @Delete
    void deleteResource(Resource resource);

    @Update
    void updateResource(Resource resource);

    @Query("SELECT * FROM resource")
    LiveData<List<Resource>> findAllResources();
}
