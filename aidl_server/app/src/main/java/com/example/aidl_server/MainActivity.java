package com.example.aidl_server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.aidl_server.adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ImageButton button;

    private ListView listView;

    private List<ContactBO> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},null,null,null);
        while (cursor.moveToNext()){
            ContactBO contactBO = new ContactBO(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            contacts.add(contactBO);
        }
        listView = findViewById(R.id.list);
        listView.setAdapter(new ContactAdapter(contacts));
        button = findViewById(R.id.transfer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoteService.setContacts(contacts);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.example.aidl_client", "com.example.aidl_client.MainActivity"));
                startActivity(intent);
            }
        });
    }

}
