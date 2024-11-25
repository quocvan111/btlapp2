package com.example.btl1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    //tạo bảng
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_DATE + " INTEGER)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Tạo bảng
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Xóa bảng cũ
        onCreate(db); // Tạo bảng mới
    }
    // Phương thức để thêm task vào cơ sở dữ liệu
    public void addTask(String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu để ghi
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_NAME, null, values); // Thêm bản ghi vào bảng
        db.close(); // Đóng cơ sở dữ liệu
    }
    // Hàm cập nhật giá trị COLUMN_DESCRIPTION
    public void updateDescription(int id, String newDescription) {
        // Mở kết nối cơ sở dữ liệu
        SQLiteDatabase db = this.getWritableDatabase();

        // Tạo ContentValues để cập nhật
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, newDescription);

        // Cập nhật giá trị description theo ID
        int rows = db.update("tasks", values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        // Đóng kết nối

        db.close();
    }
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ cursor
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));

                // Thêm vào danh sách
                taskList.add(new Task(description, date,id));
            } while (cursor.moveToNext());
        }

        cursor.close(); // Đóng cursor
        db.close(); // Đóng cơ sở dữ liệu
        return taskList;
    }
    public List<Task> getTasksByDate(String date) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = ?";
        // Truy vấn lấy các task theo ngày
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String datebytask = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                // Thêm vào danh sách
                taskList.add(new Task(description, datebytask,id));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return taskList;
    }
    //xóa 1 task
    public void deleteTask(int idtask) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id = ?", new String[]{String.valueOf(idtask)});
        db.close();
    }


}

