package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TODOViewModel extends AndroidViewModel {
    private TODORepository todoRepository;
    private LiveData<List<TODO>> todoList;
     public TODOViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TODORepository(application);
        todoList = todoRepository.getTodoList();
    }

    public void insert(TODO todo){
         todoRepository.insertData(todo);
    }
    public void delete(TODO todo){
        todoRepository.deleteData(todo);
    }
    public void update(TODO todo){
        todoRepository.updateData(todo);
    }

    public LiveData<List<TODO>> getTodoList(){
        return todoList;
    }
}
