package com.example.todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    public void insert(TODO todo);

    @Update
    public void update(TODO todo);

    @Delete
    public void delete(TODO todo);

    @Query("SELECT * FROM todoList")
    public LiveData<List<TODO>> getAllData();
}
