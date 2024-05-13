package com.example.bai2_lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context mContext;
    private List<Contact> mContacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, 0, contacts);
        mContext = context;
        mContacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_items,parent,false);
        Contact currentContact = mContacts.get(position);
        TextView idTextView = convertView.findViewById(R.id.tv_id);
        idTextView.setText(String.valueOf(currentContact.getId()));
        TextView nameTextView = convertView.findViewById(R.id.tv_name);
        nameTextView.setText(currentContact.getName());
        TextView phoneTextView = convertView.findViewById(R.id.tv_phone);
        phoneTextView.setText(currentContact.getPhoneNumber());
        return convertView;
    }
}

