package com.example.btl1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>  {
    List<Task> listtask;
    private Context context;

    public TaskAdapter(Context context, List<Task> listtask) {
        this.context = context;
        this.listtask = listtask;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = listtask.get(position);
        holder.titleoutput.setText(task.getDescription());
        holder.itemView.setOnClickListener(v -> showPopupMenu(v, position));

    }

    @Override
    public int getItemCount() {
        return listtask.size();
    }
    private void showPopupMenu(View view, int position) {
        // Tạo PopupMenu
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.popupmenu);

        // Lắng nghe sự kiện khi nhấn vào các mục trong PopupMenu
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.edit) {
                // Xử lý chỉnh sửa
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id", listtask.get(position).getId());
                intent.putExtra("description", listtask.get(position).getDescription());
                intent.putExtra("date",listtask.get(position).getDatenote());
                context.startActivity(intent);
                Toast.makeText(context, listtask.get(position).getId() + listtask.get(position).getDescription() + listtask.get(position).getDatenote(), Toast.LENGTH_SHORT).show();

                return true;
            } else if (itemId == R.id.delete) {
                // Xử lý xóa
                int deletetask = listtask.get(position).getId();  // Giả sử bạn có phương thức getId() trong Task
                deleteTaskFromDatabase(deletetask);  // Gọi hàm để xóa trong cơ sở dữ liệu
                listtask.remove(position);  // Xóa item khỏi danh sách
                notifyItemRemoved(position);  // Cập nhật RecyclerView
                notifyItemRangeChanged(position, listtask.size());
                return true;
            }
            return false;
        });

        // Hiển thị PopupMenu
        popupMenu.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleoutput;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleoutput = itemView.findViewById(R.id.textoutput);
        }
    }

    // lấy hàm xóa item từ DatabaseHelper
    private void deleteTaskFromDatabase(int idtask) {
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteTask(idtask);  // Xóa từ database
    }
//    private void updateTaskInDatabase(int taskId, String newDescription, String newDateNote) {
//        // Thực hiện cập nhật trong cơ sở dữ liệu SQLite
//        SQLiteDatabase db  ;
//        ContentValues values = new ContentValues();
//        values.put("description", newDescription);
//        values.put("datenote", newDateNote);
//
//        db.update("tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
//        db.close();
//    }

}
