package com.example.btl1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    EditText editText;
    Button btnsave;
    private DatabaseHelper databaseHelper;
    //private TaskAdapter taskAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewnote);

        editText = findViewById(R.id.titleinput);
        databaseHelper = new DatabaseHelper(this);
        //lấy date từ intent
        Intent intent = getIntent();
        long datenoteMillis = intent.getLongExtra("day", 0);
        Date datenote = new Date(datenoteMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(datenote);

        btnsave  = findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText.getText().toString();
                databaseHelper.addTask(title, formattedDate);
                finish();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
               
                MainActivity.calendarView.setDate(System.currentTimeMillis(), true, true);
            }
        });
    }
}
