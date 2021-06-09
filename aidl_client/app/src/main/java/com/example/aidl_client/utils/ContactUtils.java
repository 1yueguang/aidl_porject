package com.example.aidl_client.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.aidl_server.ContactBO;

import java.util.List;

public class ContactUtils {

    private static Context context;

    public static void init(Context _context){
        context = _context;
    }

    public static void deleteContact(String name){
        System.out.println(name);
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.Contacts.Data._ID},"display_name=?", new String[]{name+""}, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            context.getContentResolver().delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            context.getContentResolver().delete(uri, "raw_contact_id=?", new String[]{id+""});
        }
        Toast.makeText(context, "联系人数据：" + name + "，删除成功", Toast.LENGTH_SHORT).show();
    }

    public static boolean addContact(ContactBO contactBO){
        if ("".equals(contactBO.getName()) || "".equals(contactBO.getPhone())){
            return false;
        }
        ContentValues values = new ContentValues();
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactBO.getName());
        // 向联系人URI添加联系人名字
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contactBO.getPhone());
        // 电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        values.clear();

        return true;
    }

    public static List<ContactBO> queryContacts(){
        return null;
    }



}
