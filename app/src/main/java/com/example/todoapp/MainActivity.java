package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private TODOViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        todoViewModel = new ViewModelProvider(this,(ViewModelProvider.Factory)ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(TODOViewModel.class);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent,1);
            }
        });

        binding.RV.setLayoutManager(new LinearLayoutManager(this));
        binding.RV.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter(MainActivity.this);
        binding.RV.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction==ItemTouchHelper.RIGHT)
                {
                    todoViewModel.delete(adapter.getTodo(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getTodo(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disp",adapter.getTodo(viewHolder.getAdapterPosition()).getDisplay());
                    intent.putExtra("date",adapter.getTodo(viewHolder.getAdapterPosition()).getDate());
                    intent.putExtra("id",adapter.getTodo(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2 );
                }

            }
        }).attachToRecyclerView(binding.RV);

        todoViewModel.getTodoList().observe(this, new Observer<List<TODO>>() {
            @Override
            public void onChanged(List<TODO> todos) {
                adapter.submitList(todos);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            String date = data.getStringExtra("date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date todoDate = null;
            try{
                todoDate = dateFormat.parse(date);
            }catch(ParseException e){
                throw new RuntimeException(e);
            }
            TODO todo = new TODO(title,disp,todoDate);
            todoViewModel.insert(todo);
            Toast.makeText(this, "TODO List added", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==2)
        {
            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("disp");
            String date = data.getStringExtra("date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date todoDate = null;
            try{
                todoDate = dateFormat.parse(date);
            }catch(ParseException e){
                throw new RuntimeException(e);
            }
            TODO todo = new TODO(title,disp,todoDate);
            todoViewModel.insert(todo);
            Toast.makeText(this, "TODO List updated", Toast.LENGTH_SHORT).show();

        }
    }

}