package com.example.bai3_lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private Context context;
    private static List<student> students;
    private static ItemClickListener listener = null;
    public StudentAdapter(Context context, List<student>
            objects, ItemClickListener itemClickListener) {
        this.context = context;
        this.students = objects;
        this.listener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View employeeView = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(employeeView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        student student1 = students.get(position);
        holder.textView.setText(student1.getMssv() + " - " + student1.getName() + " - " + student1.get_class());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void addStudent(student student1) {
        this.students.add(student1);
    }
   public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText edMSSV, edName, edClass;
        private TextView textView;
        public ViewHolder(@NonNull View itemView, ItemClickListener listener1) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_Result);
            edMSSV = itemView.findViewById(R.id.ed_mssv);
            edName = itemView.findViewById(R.id.ed_name);
            edClass = itemView.findViewById(R.id.ed_class);
            itemView.setOnClickListener(this);
        }
       @Override
       public void onClick(View v) {
           if(listener != null)
           {
               int pos= getAdapterPosition();
               if(pos!= RecyclerView.NO_POSITION)
               {
                   listener.onClick(pos);
               }
           }
       }
   }
}
