package com.example.bai2_lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, contact.getName());
        initialValues.put(KEY_PH_NO, contact.getPhoneNumber());
//        contact.setId((int) db.insert(TABLE_CONTACTS, null, initialValues));
        db.insert(TABLE_CONTACTS, null, initialValues);
        db.close();
    }

    public Contact getContact(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                KEY_NAME, KEY_PH_NO}, KEY_ID+"="+ id, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst() ) {
            int nameColumnIndex = cursor.getColumnIndex(KEY_NAME);
            int phoneColumnIndex = cursor.getColumnIndex(KEY_PH_NO);
            String name = cursor.getString(nameColumnIndex);
            String phoneNumber = cursor.getString(phoneColumnIndex);
            Contact contact = new Contact(name, phoneNumber);
            cursor.close();
            return contact;
        }
        return null;
    }
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                KEY_NAME, KEY_PH_NO}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst() ) {
            do {
                int idColumnIndex =cursor.getColumnIndex(KEY_ID);
                int nameColumnIndex = cursor.getColumnIndex(KEY_NAME);
                int phoneColumnIndex = cursor.getColumnIndex(KEY_PH_NO);
                int id = Integer.parseInt(cursor.getString(idColumnIndex));
                String name = cursor.getString(nameColumnIndex);
                String phoneNumber = cursor.getString(phoneColumnIndex);
                Contact contact = new Contact( name, phoneNumber, id);
                contacts.add(contact);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return contacts;
    }
    public int updateContact(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PH_NO,contact.getPhoneNumber());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_CONTACTS, contentValues,KEY_ID+ "=?", new String[]{String.valueOf(contact.getId())});
    }
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_ID+"=?", new String[]{String.valueOf(id)});
    }
    public boolean deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACTS, null, null) > 0;
    }
}
