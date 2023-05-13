package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todoapp.databinding.ActivityDataInsertBinding;

import java.util.Calendar;

public class DataInsertActivity extends AppCompatActivity {

    ActivityDataInsertBinding binding;
    TextView editDateText;
    int year;
    int month;
    int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Date Time
        editDateText = findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateExtra = getIntent().getStringExtra("data");
        if(dateExtra != null && !dateExtra.isEmpty()){
            editDateText.setText(dateExtra);
        }

        editDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DataInsertActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editDateText.setText(selectedDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        String type = getIntent().getStringExtra("type");
        if (type.equals("update")) {
            setTitle("update");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.disp.setText(getIntent().getStringExtra("disp"));
            binding.editTextDate.setText(getIntent().getStringExtra("date"));
            int id = getIntent().getIntExtra("id", 0);
            binding.add.setText("Update List");

            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.disp.getText().toString());
                    intent.putExtra("date",binding.editTextDate.getText().toString());
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        } else {
            setTitle("Add Mode");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.disp.getText().toString());
                    intent.putExtra("date",binding.editTextDate.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            });
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this,MainActivity.class));
    }
}