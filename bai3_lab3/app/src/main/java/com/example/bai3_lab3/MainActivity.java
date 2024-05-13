package com.example.bai3_lab3;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener{
    List<student> students;
    EditText edMSSV, edName, edClass;
    RecyclerView recyclerView;
    Button btn_add;
    Button btn_del;
    Button btn_update;
    Button btn_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edMSSV = findViewById(R.id.ed_mssv);
        edName = findViewById(R.id.ed_name);
        edClass = findViewById(R.id.ed_class);
        btn_add = findViewById(R.id.btn_add);
        btn_del = findViewById(R.id.btn_del);
        btn_update = findViewById(R.id.btn_update);
        btn_query = findViewById(R.id.btn_query);
        recyclerView = findViewById(R.id.rv_Result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHandle db = new DatabaseHandle(this);
        Log.e("Reading: ", "Reading all contacts..");
        students = db.getAllStudents();
        showData(students);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student student = new student(edMSSV.getText().toString(), edName.getText().toString(), edClass.getText().toString());
                db.addStudent(student);
                showData(db.getAllStudents());
                edMSSV.setText("");
                edName.setText("");
                edClass.setText("");
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                db.deleteStudent(edMSSV.getText().toString());
                showData(db.getAllStudents());
                edMSSV.setText("");
                edName.setText("");
                edClass.setText("");
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student student = new student(edMSSV.getText().toString(), edName.getText().toString(), edClass.getText().toString());
                db.updateStudent(student);
                showData(db.getAllStudents());
                edMSSV.setText("");
                edName.setText("");
                edClass.setText("");
            }
        });
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<student> studentLis = db.getStudent(edMSSV.getText().toString());
                showData(studentLis);
                if(studentLis.size()==0){
                    Toast.makeText(MainActivity.this,"Không tìm thấy sinh viên!",Toast.LENGTH_SHORT).show();
                }
                edMSSV.setText("");

            }
        });
    }
    public void showData(List<student> students) {
        StudentAdapter adapter = new StudentAdapter(MainActivity.this, students,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        student student = students.get(position);
        edMSSV.setText(student.getMssv());
        edName.setText(student.getName());
        edClass.setText(student.get_class());
    }
}