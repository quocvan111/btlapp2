package com.example.btl1;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    TextView textView;
    EditText editText,editText1;
    Button btnupdate;
    DatabaseHelper databaseHelper;
    String formattedDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatetask);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        String description = intent.getStringExtra("description");
        editText = findViewById(R.id.txtupdate);
        editText.setText(description);

        long datenoteMillis = intent.getLongExtra("day", 0);
        Date datenote = new Date(datenoteMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(datenote);

        btnupdate = findViewById(R.id.btnupdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newdescription = editText.getText().toString();
                updateDescription(id, newdescription);
                Intent intent1 = new Intent(UpdateActivity.this, NewAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                sendBroadcast(intent);
            }
        });
    }
    private void updateDescription(int id, String newDescription) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateDescription(id, newDescription);
        Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);


        sendBroadcast(intent);
        finish(); // Đóng Activity sau khi cập nhật

    }
}
