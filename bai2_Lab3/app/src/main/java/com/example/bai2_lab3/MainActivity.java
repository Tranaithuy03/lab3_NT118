package com.example.bai2_lab3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteAllContacts();
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
         contacts= db.getAllContacts();
         listView = findViewById(R.id.lv_contact);
         showData(contacts);
         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 Contact contactToDelete = contacts.get(position);
                 int contactId = contactToDelete.getId();
                 db.deleteContact(contactId);
                 showData(db.getAllContacts());
                 return true;
             }

         });
    }

    public void showData(List<Contact> contact) {
        ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this, contact);
        listView.setAdapter(contactAdapter);
    }
}