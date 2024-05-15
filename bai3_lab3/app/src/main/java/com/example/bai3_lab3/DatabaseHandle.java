package com.example.bai3_lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandle extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "studentsManager";
    // Contacts table name
    private static final String TABLE_STUDENT = "students";
    // Contacts Table Columns names
    private static final String KEY_MSSV = "mssv";
    private static final String KEY_NAME = "name";
    private static final String KEY_CLASS = "class";
    public DatabaseHandle(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_STUDENT + "("
                + KEY_MSSV + " VARCHAR(25) PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CLASS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }
    public void addStudent(student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MSSV, student.getMssv());
        initialValues.put(KEY_NAME, student.getName());
        initialValues.put(KEY_CLASS, student.get_class());
        db.insert(TABLE_STUDENT, null, initialValues);
    }

    public List<student> getStudent(String mssv) {
        List<student> students= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {mssv};
        Cursor cursor= db.query(TABLE_STUDENT, new String[]{KEY_MSSV,
                KEY_NAME, KEY_CLASS}, KEY_MSSV+"=?", selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst() ) {
            do {
                int idColumnIndex =cursor.getColumnIndex(KEY_MSSV);
                int nameColumnIndex = cursor.getColumnIndex(KEY_NAME);
                int phoneColumnIndex = cursor.getColumnIndex(KEY_CLASS);
                String MSSV = cursor.getString(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String phoneNumber = cursor.getString(phoneColumnIndex);
                student student = new student(MSSV, name, phoneNumber);
                students.add(student);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return students;
    }
    public int updateStudent(student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(KEY_CLASS ,student.get_class());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_STUDENT, contentValues,KEY_MSSV+ "=?", new String[]{String.valueOf(student.getMssv())});
    }
    public void deleteStudent(String mssv) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT,KEY_MSSV+"=?", new String[]{mssv});
        db.close();
    }
    public List<student> getAllStudents() {
        List<student> students= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.query(TABLE_STUDENT, new String[]{KEY_MSSV,
                KEY_NAME, KEY_CLASS}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst() ) {
            do {
                int idColumnIndex =cursor.getColumnIndex(KEY_MSSV);
                int nameColumnIndex = cursor.getColumnIndex(KEY_NAME);
                int phoneColumnIndex = cursor.getColumnIndex(KEY_CLASS);
                String mssv = cursor.getString(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String phoneNumber = cursor.getString(phoneColumnIndex);
                student student = new student(mssv, name, phoneNumber);
                students.add(student);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return students;
    }
    public boolean deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENT, null, null) > 0;
    }
}
