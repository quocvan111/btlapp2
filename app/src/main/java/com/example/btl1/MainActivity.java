package com.example.btl1;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static CalendarView calendarView;
    Button btnaddnew;
    RecyclerView recyclerView;
    Calendar calendar;
    private Date datenote;
    private DatabaseHelper databaseHelper;
    public TaskAdapter taskAdapter;
    List<Task> taskList;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //khởi tạo day và recyclerView khi mới bắt đầu 39-55
        //Calendar và thiết lập ngày tháng năm
        calendar = calendar.getInstance();
        datenote = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formattedDate = dateFormat.format(calendar.getTime());

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(MainActivity.this);
        // Lấy tất cả các task vụ từ cơ sở dữ liệu
        taskList = databaseHelper.getTasksByDate(formattedDate);
        //Gán dữ liệu cho recyclerView
        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(MainActivity.this,taskList);
        recyclerView.setAdapter(taskAdapter);


        calendarView = findViewById(R.id.calendarview);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                datenote = calendar.getTime();
                //định dạng date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                formattedDate = dateFormat.format(calendar.getTime());

                // Khởi tạo DatabaseHelper
                databaseHelper = new DatabaseHelper(MainActivity.this);
                // Lấy tất cả các task vụ từ cơ sở dữ liệu
                taskList = databaseHelper.getTasksByDate(formattedDate);
                //Gán dữ liệu cho recyclerView
                recyclerView = findViewById(R.id.recyclerview);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
                taskAdapter = new TaskAdapter(MainActivity.this,taskList);
                recyclerView.setAdapter(taskAdapter);

                Intent intent = new Intent(MainActivity.this, NewAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                // Truyền dữ liệu ngày đã chọn
                intent.putExtra("SELECTED_DATE", formattedDate);

                // Lấy tất cả các ID widget
                int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

                sendBroadcast(intent);
            }
        });

        btnaddnew = findViewById(R.id.addnew);
        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //truyền date đi AddActivity.class
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("day", datenote.getTime());
                startActivity(intent);

            }
        });
    }
}