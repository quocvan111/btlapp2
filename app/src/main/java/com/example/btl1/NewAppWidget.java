package com.example.btl1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String currentDate) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Task> tasks = databaseHelper.getTasksByDate(currentDate);

        // Tạo StringBuilder để kết hợp tất cả các mô tả
        StringBuilder taskDescriptions = new StringBuilder();
        String a ="";
        // Lặp qua tất cả các task và thêm mô tả vào StringBuilder
        for (int i=0;i<tasks.size();i++) {
            a+= tasks.get(i).getDescription()+ "\n";
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.day_wiget,currentDate);
        views.setTextViewText(R.id.textview_wiget,a);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, currentDate);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            String selectedDate = intent.getStringExtra("SELECTED_DATE");
            if (selectedDate != null) {
                // Cập nhật widget với ngày đã chọn
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, selectedDate);
                }
            }
        }
    }
}